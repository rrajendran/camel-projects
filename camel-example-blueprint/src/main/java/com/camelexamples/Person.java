package com.camelexamples;

/**
 * Created by ramesh on 05/10/2015.
 */
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    private Person(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        age = builder.age;
    }

    public static Builder getInstance() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    /**
     * {@code Person} builder static inner class.
     */
    public static final class Builder {
        private String firstName;
        private String lastName;
        private int age;

        private Builder() {
        }

        /**
         * Sets the {@code firstName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code firstName} to set
         * @return a reference to this Builder
         */
        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        /**
         * Sets the {@code lastName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code lastName} to set
         * @return a reference to this Builder
         */
        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        /**
         * Sets the {@code age} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code age} to set
         * @return a reference to this Builder
         */
        public Builder age(int val) {
            age = val;
            return this;
        }

        /**
         * Returns a {@code Person} built from the parameters previously set.
         *
         * @return a {@code Person} built with parameters of this {@code Person.Builder}
         */
        public Person build() {
            return new Person(this);
        }
    }
}
