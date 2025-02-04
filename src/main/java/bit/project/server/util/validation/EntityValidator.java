package bit.project.server.util.validation;

import bit.project.server.util.exception.DataValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public class EntityValidator {

    public static void validate(Object object){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = validatorFactory.getValidator();
        ValidationErrorBag errorBag = new ValidationErrorBag();

        Set<ConstraintViolation<Object>> violations;

        try{
             violations = validator.validate(object);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        for (ConstraintViolation v : violations) {
            errorBag.add(v.getPropertyPath().toString(), v.getMessage());
        }

        if(errorBag.count()>0){ throw new DataValidationException(errorBag); }
    }

}
