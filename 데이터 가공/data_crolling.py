from bs4 import BeautifulSoup as bs
import time
import requests


check="http://icanhazip.com/"
proxies={
    'http':'socks5://127.0.0.1:9050',
    'https':'socks5://127.0.0.1:9050',
}
print("start")
c_res =  requests.get(check,proxies=proxies)
print(c_res.text)
"""
with open("elementary_3.txt","r",encoding="utf-8") as f:
    lines = f.readlines()
    elementary_3_mean = open("ele_3_mean.txt","w",encoding="utf-8")
    for line in lines:
        word = line.strip()
        time.sleep(2)
        #너무 빨리하면 다음에서 밴먹음
        url  = "https://dic.daum.net/search.do?q="+word+"&dic=kor"
        html_text = requests.get(url).text
        soup_obj = bs(html_text,"html.parser")
        res = soup_obj.find('meta',property =  "og:description")
        res = str(res)
        if res == None:
            continue
        flag =0
        temp =""
        for i in range(len(res)):
            if res[i]=="\"" and flag ==0:
                flag =1
            elif res[i] =="\"" and flag ==1:
                flag=0
                break
            elif flag ==1:
                temp+=res[i]
        print(word)
        print(temp)
        elementary_3_mean.write(word+"\n "+temp+"\n")
    elementary_3_mean.close()
"""