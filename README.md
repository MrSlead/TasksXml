### Задачи для XML

#### Задача 1
На основании запроса SELECT ID_ART,NAME,CODE,USERNAME,GUID FROM WHS.ARTICLE where rownum < 10000<br>
Сформировать XML вида: <br>
```
<articles>
    <article id_art="1" name="name1" code="code1" username="username1" guid="guid1"/>
    <article id_art="2" name="name2" code="code2" username="username2" guid="guid2"/>
    ...
</articles>
```

#### Задача 2
Написать XSLT преобразование, которое приведет xml, полученный в предыдущей задаче к виду:<br>
```
<articles>
    <article>
        <id_art>1</id_art>
        <name>name1</name>
        <code>code1</code>
        <username>username1</username>
        <guid>guid1</guid>
    </article>
    <article>
        <id_art>2</id_art>
        <name>name2</name>
        <code>code2</code>
        <username>username2</username>
        <guid>guid2</guid>
    </article>
    ...
</articles>
```

#### Задача 3
Написать программу, которая из xml, полученного из предыдущей задачи после преобразования, сформирует CSV файл вида<br>
```
ID_ART,NAME,CODE,USERNAME,GUID

1,name1,code1,username1,guid1
2,name2,code2,username2,guid2
3,name3,code3,username3,guid3
...
```