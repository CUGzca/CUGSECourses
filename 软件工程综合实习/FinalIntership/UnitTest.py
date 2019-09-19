import unittest

def LogupFuction(telephone,password1,password2):
    if len(telephone)==0 or telephone=="15586920520":
        return False
    if password1!=password2:
        return False
    return True

def LoginFunction(telephone,password):
    if (len(telephone)==0) or (len(password)==0):
        return False
    if telephone=="15586920520" and password=="123456789":
        return True
    else:
        return False

class LoginTest(unittest.TestCase):
    def test_login(self):
        self.assertEqual(True,LoginFunction("15586920520","123456789"))
        self.assertEqual(False,LoginFunction("15586920520","12345678"))
        self.assertEqual(False,LoginFunction("","12345678"))
        self.assertEqual(False,LoginFunction("15586920520",""))
        self.assertEqual(True, LoginFunction("15586920520", "123456789"))
        self.assertEqual(True, LoginFunction("15586920520", "123456789"))
        self.assertEqual(True, LoginFunction("15586920520", "123456789"))
        self.assertEqual(False, LoginFunction("15586920520", "12345678"))
        self.assertEqual(False, LoginFunction("15586920520", "123456782222"))
        self.assertEqual(False, LoginFunction("15586920521", "12345678"))
        self.assertEqual(False, LoginFunction("155821", "102345678"))

    def test_logup(self):
        self.assertEqual(False, LogupFuction("15586920520", "123456789","123456789"))
        self.assertEqual(False, LogupFuction("15586920521", "123456789","1235"))
        self.assertEqual(False, LogupFuction("15586920522", "12346789","12355225"))

unittest.main()
