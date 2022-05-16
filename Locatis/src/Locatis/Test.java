package Locatis;

import DAO.*;
import java.sql.*;
import java.util.ArrayList;

public class Test {
    public static void main (String args[]){
        Mailer mail = new Mailer();
        ArrayList<String> listMail = new ArrayList<>();
        listMail.add("benjaminrandazzo2@hotmail.fr");
        listMail.add("benjamin.randazzo.auditeur@lecnam.net");
        mail.sendEmail("theluffydu30@gmail.com","bvogzfkcpurcwnnc","Message de test","Ceci est un message de test", listMail);
    }
}
