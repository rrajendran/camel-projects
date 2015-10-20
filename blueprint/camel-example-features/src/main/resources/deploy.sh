#!/bin/sh

# set debug mode
#set -x

alias client_cmd="/opt/fuse/bin/client -p admin -u admin "

gitAdd(){
	mkdir -p /tmp/fuse_git
	cd /tmp/fuse_git/

	rm -rf *
	rm -rf .git

	git clone http://admin:admin@localhost:8181/git/fabric
	cd /tmp/fuse_git/fabric/fabric/

	git checkout $1

	echo "Listing properties after checkout"

	ls -lrt /tmp/fuse_git/fabric/fabric/profiles/ssb/profile.profile

	cp -R ~/stash/esb/jbossfuse/Function/ssb-common/src/main/resources/properties/ssb/* /tmp/fuse_git/fabric/fabric/profiles/ssb/profile.profile
	cp -R ~/stash/esb/jbossfuse/Function/ssb-common/src/main/resources/properties/system/* /tmp/fuse_git/fabric/fabric/profiles/ssb/profile.profile

	ls -lrt /tmp/fuse_gitfabric/fabric/profiles/ssb/profile.profile

	cd /tmp/fuse_git/fabric/fabric/profiles/ssb/profile.profile

	pwd

	git add .

	git commit -m "comitting properties for version $1"

	git push

	echo "********Finished git comitting**********"
}

#create features function
createFeatures (){
	echo "Adding features"
	while read -r line; do
		client_cmd ="fabric:profile-edit --features $line $1 $2"
	done < $3/features.txt
}

createBaseProfile(){
	echo "-----------Creating Base Profile---- $1----$2------"
	client_cmd "fabric:profile-create --parents jboss-fuse-minimal --version $1 $2"
	echo "Base Profile created "

}

createProfile(){
	echo "-----------Creating Profile---- $1----$2----$3-----------"
	client_cmd "fabric:profile-create --parents jboss-fuse-minimal --version $1 $2"
	echo "Profile created "
	client_cmd "fabric:profile-edit --repositories mvn:com.camelexamples/camel-example-features/$3/xml/features $2 $1"
	echo "Repository added"
}

cleanContainer(){
	client_cmd "fabric:container-delete  --force $2"
	echo "Container deleted"
	echo "----------------------------------"
	client_cmd "fabric:version-delete $1"
	echo "Version deleted"
	echo "----------------------------------"
	client_cmd "fabric:version-create $1"
	echo "Version created"
}

crateFabric(){
	client_cmd = "fabric:create --wait-for-provisioning --global-resolver localhostname --zookeeper-password admin"
}

mq(){
	client_cmd ="fabric:import --profile ssb-profile --version $1 -t /fabric/configs/versions/$1/profiles/mq-base/ssb-broker.xml /home/ssb/stash/esb/jbossfuse/Function/ssb-common/src/main/resources/activemq/broker.xml"
	client_cmd="fabric:mq-create --assign-container ssb-fuse --config ssb-broker.xml --version $1 --profile ssb-profile ssb-broker"
}

createContainer(){
	echo "creating container $1, $2"
	client_cmd ="fabric:container-create-child --profile $1 --version $2 --jvm-opts '-Xms256M -Xmx1024M -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044' --jmx-password admin  root $3 "
	client_cmd ="fabric:container-add-profile $3 $1"

}

mvnOverride(){
	client_cmd " fabric:profile-edit --pid io.fabric8.agent/override.mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.jaxb-impl/2.2.1.1_2=mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.jaxb-impl/2.2.10_1 $1 $2"
}

#properties
version="$1"
repo_version="$2"
container_name="camel-examples-fuse"
amq_container="ssb-amq"
profile_name="camel-examples-profile"
search_dir="properties"
base_dir="$3"

#function calls
cleanContainer $version $container_name
#createBaseProfile 1.0 $base_profile_name
createProfile $version $profile_name $repo_version
#mvnOverride $profile_name $version
createFeatures $profile_name $version $base_dir
#gitAdd $version
createContainer $profile_name $version $container_name

echo "Finished deploying"