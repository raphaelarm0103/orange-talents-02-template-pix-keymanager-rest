package br.com.zup.edu.shared.validations

import javax.validation.Constraint
import javax.validation.ReportAsSingleViolation
import javax.validation.constraints.Pattern

@ReportAsSingleViolation
@Constraint(validatedBy = [])
@Pattern(
    regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
    flags = [Pattern.Flag.CASE_INSENSITIVE]
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class ValidUUID(val message: String = "Preencher com formato válido UUID")