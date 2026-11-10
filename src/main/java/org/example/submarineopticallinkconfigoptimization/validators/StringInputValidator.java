package org.example.submarineopticallinkconfigoptimization.validators;

import com.dlsc.formsfx.model.validators.CustomValidator;


public class StringInputValidator extends CustomValidator<String> {

    public StringInputValidator(int max, String errorMessage) {
        super(input-> !input.isBlank()&& input.length()<=max, errorMessage);
    }
}
