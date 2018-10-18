package com.java;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.java.dto.Address;
import com.java.dto.Student;

public class Main {
	
	static SessionFactory sf;
	static {
			Configuration cfg=new Configuration().addPackage("com.java.dto");
			cfg.configure("hibernate.cfg.xml");
			sf=cfg.buildSessionFactory();
	}
public void insertDemo() {
	Address address= new Address(1);
	Student st= new Student(1,"Payal",address);
	Session s=sf.openSession();
	Transaction t=s.beginTransaction();
	s.save(address);
	s.save(st);
	t.commit();
	s.close();
}
	public void loadDemo() {
		Session s=sf.openSession();
		Student st=s.load(Student.class, 1); //no query would be fired to db. 
		//It will just return a proxy object
		//It creates a proxy class: and it will return the object of that class just populating the primary key id
		//lazy fetch
		
		
		System.out.println(1);
		System.out.println(st.getName()); //At this time it will go and fire query to db and populate this object
	}

	public void getDemo() {
		Session s=sf.openSession();
		Student st=s.get(Student.class, 1);//eager fetch ...It will fire a query to db
		System.out.println(2);
		System.out.println(st.getAddress());
	}
	
	public void loadDemo1() {
		System.out.println("In load");
		Session s= sf.openSession();
		Transaction t=s.beginTransaction();
		//Address ad= s.load(Address.class, 1); //proxy object
		Address ad= new Address(2);
		s.save(ad);
		Student st=s.load(Student.class, 1);//proxy object
		//st.setName("apa");
		st.setAddress(ad);//select query on student : 1 //persistent//update
		t.commit();
		s.close();
	}
	
	//delete | update: select : interceptor
	
	public void mergeDemo() {
		Session s= sf.openSession();
		s.beginTransaction();
		Student st= s.get(Student.class, 1); //Persistent
		st.setAddress(null);
		s.getTransaction().commit();
		s.close();// detached
		st.setName("ritu");
		Session s1= sf.openSession();
		Student st1= s1.load(Student.class, 1); 
		st1.setName("parul");//Persistent
		Transaction t1=s1.beginTransaction();
	//	s1.update(st); //Detached state: persistent: update //error
		s1.update(st1);//this will work
		//s1.merge(st);//ritu
		s1.merge(st1);//parul
		t1.commit();
		System.out.println(s1.get(Student.class, 1));
		s1.close();
	}
public static void main(String args[]) {
	Main obj= new Main();
	//obj.insertDemo();
	//System.out.println("Inserted");
//	obj.loadDemo();
	//obj.getDemo();
	obj.loadDemo1();
	//obj.mergeDemo();
	sf.close();
}
}
