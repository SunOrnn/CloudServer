package com.ornn.schedule.wallet.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValue.EnumValueValidator.class})
public @interface EnumValue {

    String message() default "必须为指定值";
    String[] strValues() default {};
    int[] intValues() default {};
    //分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE,PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValue[] value();
    }

    class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
        private String[] strValues;
        private int[] intValues;


        @Override
        public void initialize(EnumValue constraintAnnotation) {
            strValues = constraintAnnotation.strValues();
            intValues = constraintAnnotation.intValues();
        }

        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (o instanceof String) {
                for (String s : strValues) {
                    if (s.equals(strValues)) {
                        if (s.equals(o)) {
                            return true;
                        }
                    }
                }
            } else if (o instanceof Integer) {
                for (Integer s : intValues) {
                    if (s == o) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
