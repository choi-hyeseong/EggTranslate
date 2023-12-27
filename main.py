from requests_toolbelt.multipart.encoder import MultipartEncoder
import requests
import os
import json
from tkinter import filedialog

def img_post():
    url = 'http://localhost:8080/api/image/upload'
    filename = filedialog.askopenfilename(initialdir="/", title="파일을 선택해주세요")
    body = MultipartEncoder(
        fields={
            'image': (os.path.basename(filename), open(filename, 'rb'), 'image')
        }
    )
    headers = {'Content-type': body.content_type}
    response = requests.post(url, headers=headers, data=body, timeout=50000)
    return response


def post():
    content = input("문장 입력 : ")
    url = 'http://localhost:8080/api/image/'
    headers = {'Content-type': 'application/json'}
    body = {'from': 'ko', 'to': 'en', 'content': content}
    response = requests.post(url, headers=headers, data=json.dumps(body))
    return response

"""
res = post()
print(res.text)
print(res.status_code)

"""
res = img_post()
print(json.dumps(json.loads(res.text), indent=3, ensure_ascii=False))
print(res.status_code)
