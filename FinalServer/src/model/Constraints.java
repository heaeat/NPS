package model;

import java.io.Serializable;

public class Constraints implements Serializable   {

   private String consName;
   
   public Constraints(String consName){
      this.consName = consName;
   }
   public String getConsName(){
      return this.consName;
   }
}