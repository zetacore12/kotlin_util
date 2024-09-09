package org.util.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

annotation class MailAddress (
        val message: String = "Invalid Email.",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
) {
    companion object{
        const val PATTERN = "^[\\w!#$%&'*+\\-\\/=?^`{|}~]+(\\.[\\w!#$%&'*+\\-\\/=?^`{|}~]+)*@[a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?([.][a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?)*[.][a-zA-Z]{2,}$"
    }
}

class MailAddressValidator: ConstraintValidator<MailAddress, String>{
    override fun isValid(mailAddress: String, context: ConstraintValidatorContext): Boolean {
        return Regex(MailAddress.PATTERN).matches(mailAddress)
    }
}