# jrs-custom-validators
Adding custom JSR-303 Validators


@ValidDate
===============
Validates if the give string has a valid startDate or not. Can provide startDate format also. Default is yyyy-MM-dd
<br />
Example
<pre>
    @ValidDate(format = "dd-MM-yyyy")
    private String date;
</pre>


@DateRange
===============

<pre>
@DateRange(fields = {"startDate","endDate"},format = "dd-MM-yyyy", message = "Given dates are not in valid range")
public class DateRangeObject {
    private String startDate;
    private String endDate;
}
</pre>

How to use it
=============

<pre>
    Set<ConstraintViolation<DateRangeObject>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(dateObject);
</pre>