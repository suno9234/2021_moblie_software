import sqlite3

path_list = [
    "데이터 가공\\완성\\ele_1_mean.txt",
    "데이터 가공\\완성\\ele_3_mean.txt",
    "데이터 가공\\완성\\ele_5_mean.txt",
    "데이터 가공\\완성\\middle_mean.txt",
    "데이터 가공\\완성\\high_mean.txt",
]
theme_list = [
    "[의사소통 목록]",
    "[일상생활 어휘]",
    "[한국어 문법]",
    "[국어]",
    "[수학]",
    "[사회]",
    "[과학]",
    "[슬기로운 생활]",
]
word, mean, theme = "", "", ""

con = sqlite3.connect("데이터 가공\\Word.db")
cursor = con.cursor()
<<<<<<< HEAD
#cursor.execute("CREATE TABLE Word(Word text, Mean text, 과목 text, 학년 text)")
file_path = 'C:\\Users\\ssh92\\AndroidStudioProjects\\Project\\데이터 가공\\완성\\ele_1_mean.txt'
with open(file_path,'r',encoding='utf-8') as f:
    lines = f.readlines()
    for line in lines:
        theme = ''
        if '[슬기로운 생활]' in line:
            theme = '슬기로운 생활'
        con.execute("INSERT ")
=======
cursor.execute("CREATE TABLE Word(word text, mean text, grade integer, theme text)")

for grade, path in enumerate(path_list):
    with open(path, "r", encoding="UTF8") as fp:
        while True:
            line = fp.readline()
            if line == "":
                break
>>>>>>> c319c5a8a6bad1d17e3cfdcf927c0b75542c8501

            l = line.strip()
            if l in theme_list:
                theme = l
                continue
            if not word and l != "\n":
                word = l
                continue
            if word:
                cursor.execute(
                    "INSERT INTO Word(word, mean, grade, theme) VALUES(?,?,?,?)",
                    (word, l, grade + 1, theme),
                )
                word = ""

con.commit()
con.close()
