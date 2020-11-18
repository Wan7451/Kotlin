package com.example.aidl1;

import com.example.aidl1.Person;

interface IMyAidlInterface {
     List<Person> addPerson(in Person person);
     //oneway 异步，不能有返回值
     oneway void addPersononly(in Person person);
}