package com.example.aidl1;

import com.example.aidl1.Person;

interface IMyAidlInterface {
    String getName(in Person person);
}