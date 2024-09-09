package org.util.annotation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl

class MailAddressTest(): FunSpec({

    val validationContext = ConstraintValidatorContextImpl(null,null,null,null,null,null)
    val validator = MailAddressValidator()

    context("local-part"){

        context("正常"){
            test("アルファベットのみ"){
                validator.isValid("abcd@gmail.com", validationContext).shouldBeTrue()
            }
            test("アルファベットをドットで区切る"){
                validator.isValid("a.b.c.d@gmail.com", validationContext).shouldBeTrue()
            }
            test("記号のみ"){
                validator.isValid("a.b.c.d@gmail.com", validationContext).shouldBeTrue()
            }
            test("記号をドットで区切る"){
                validator.isValid("a.b.c.d@gmail.com", validationContext).shouldBeTrue()
            }
        }

        context("異常"){
            test("o文字"){
                validator.isValid("@gmail.com", validationContext).shouldBeFalse()
            }
            test(".が連続している"){
                validator.isValid("ab..cd@gmail.com", validationContext).shouldBeFalse()
            }
            test(".で終わる"){
                validator.isValid("ab.cd.@gmail.com", validationContext).shouldBeFalse()
            }
            test(".で始まる"){
                validator.isValid(".ab.cd@gmail.com", validationContext).shouldBeFalse()
            }
            test("許可されていない記号一覧"){
                validator.isValid("<>()[]\\,;:@\"@gmail.com", validationContext).shouldBeFalse()
            }
            test(".で始まる"){
                validator.isValid("ab cd@gmail.com", validationContext).shouldBeFalse()
            }
            test("全角文字が入っている"){
                validator.isValid("あいうえお@gmail.com", validationContext).shouldBeFalse()
            }
            test(",が入っている"){
                validator.isValid("ab,cd@gmail.com", validationContext).shouldBeFalse()
            }
            test("@が入っている"){
                validator.isValid("ab@cd@gmail.com", validationContext).shouldBeFalse()
            }
        }
    }

    context("domain-part"){
        context("正常"){
            test("sub-domainが2つ"){
                validator.isValid("abcd@gmail.com", validationContext).shouldBeTrue()
            }
            test("sub-domainが6つ"){
                validator.isValid("abcd@aaa.bbb.ccc.ddd.eee.jp",validationContext).shouldBeTrue()
            }
            test("sub-domain(TLD以外)が数字"){
                validator.isValid("abcd@01234.com",validationContext).shouldBeTrue()
            }
            test("sub-domain(TLD以外)にハイフンが含まれる"){
                validator.isValid("abcd@gma-il.com",validationContext).shouldBeTrue()
            }
        }
        context("異常"){
            test("sub-domainが1つ"){
                validator.isValid("abcd@gmail", validationContext).shouldBeFalse()
            }
            test("TLDに数字が含まれる"){
                validator.isValid("abcd@gmail.com1", validationContext).shouldBeFalse()
            }
            test("TLDに記号が含まれる"){
                validator.isValid("abcd@gmail.co-m", validationContext).shouldBeFalse()
            }
            test("TLDが1文字"){
                validator.isValid("abcd@gmail.j", validationContext).shouldBeFalse()
            }
            test("sub-domainがハイフンで始まる"){
                validator.isValid("abcd@-gmail.com", validationContext).shouldBeFalse()
            }
            test("sub-domainがハイフンで終わる"){
                validator.isValid("abcd@gmail-.com", validationContext).shouldBeFalse()
            }
            test("sub-domainにカンマが含まれる"){
                validator.isValid("abcd@gma,il.com", validationContext).shouldBeFalse()
            }
        }
    }
})