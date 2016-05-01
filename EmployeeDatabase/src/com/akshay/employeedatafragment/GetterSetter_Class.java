package com.akshay.employeedatafragment;

public class GetterSetter_Class {
	
	private String name;
	private String updatedname;
	private int age;
	private int updatedid;
	private String experience;
	private String date;
	
	
//public Location_constructors(){
//		
//	}
	
	public GetterSetter_Class(String name,int age){ //,String designation,String experience,String date){
		this.name = name;
		this.age= age;
//		this.designation = designation;
//		this.experience = experience;
//		this.date = date;
	}
	
	public GetterSetter_Class(int id,String name){ 
		this.updatedname = name;
		this.updatedid= id;

	}
	
	public int getupdatedid(){
		return updatedid;
	}
	
	private void setupdatedid(int id){
		this.updatedid = id;
	}
	
	
	public String getupdatedname(){
		return updatedname;
	}
	
	private void setupdatedname(String name){
		this.updatedname = name;
	}
		
	public String getname()
	{
		return name;	
	}
	
	private void setname(String name)
	{
		 this.name=name;
	}
	public int getage()
	{
		return age;	
	}
	
	private void setage(int age)
	{
		 this.age=age;
	}
	
	
	

}