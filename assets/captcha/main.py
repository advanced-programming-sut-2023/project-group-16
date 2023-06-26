from captcha.image import ImageCaptcha
import random

for i in range(1000,10000):
    n = i
    image = ImageCaptcha()
    data = image.generate(str(n))
    image.write(str(n), str(n) + '.png')
