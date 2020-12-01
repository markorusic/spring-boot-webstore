package com.markorusic.webstore.util.validation;

import javax.validation.groups.Default;

public class ValidationGroup {
    public ValidationGroup() {}

    public interface Save extends Default {};

    public interface Update extends Default {};
}
