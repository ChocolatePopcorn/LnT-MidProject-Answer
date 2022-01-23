package com.LnT.MidProject;

import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    Scanner sc = new Scanner(System.in);
    Vector<Employee> employees = new Vector<Employee>();
    final double managerBonus = 0.1;
    final double supervisorBonus = 0.075;
    final double adminBonus = 0.05;

    void menu() {
        System.out.println("PT Musang");
        System.out.println("1. Insert Employee data");
        System.out.println("2. View Employee data");
        System.out.println("3. Update Employee data");
        System.out.println("4. Delete Employee data");
        System.out.println("5. Exit");
        System.out.print(">> ");
    }

    void clearScreen() {
        try {
			if(System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
			else {
				System.out.println("\033\143");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    String randomID() {
        String id = "";

        //2 random char
        for(int x=0;x<2;x++) {
            int rand = (int) (Math.random() * 26);
            id += (char)(rand + 'A');
        }

        //strip
        id += '-';
        
        //4 random int
        for(int x=0;x<4;x++) {
            int rand = (int) (Math.random() * 10);
            id += rand;
        }

        return id;
    }

    boolean checkEmployeePosition(String position) {
        int counter = 0;
        for(var i : employees) {
            if(i.getPosition().equals(position)) counter++;
        }
        
        if(counter % 3 == 1 && counter > 3) return true;
        else return false;
    }

    void updateEmployeeSalary(String id, String position) {
        double bonus = 0;
        if(position.equals("Supervisor")) bonus = supervisorBonus;
        else if(position.equals("Manager")) bonus = managerBonus;
        else if(position.equals("Admin")) bonus = adminBonus;
        
        String givenBonusId = "";
        
        for(var i : employees) {
            if(!i.getId().equals(id) && i.getPosition().equals(position)) {
                int salary = i.getSalary();
                double total = (double) (salary * bonus);
                i.setSalary((int)(total+salary)); 
                givenBonusId += i.getId() + " ";
            }
        }
        
        System.out.printf("Bonus with %.1f%%  has been given to employee with id ", (bonus*100));
        
        String[] splitted = givenBonusId.split(" ");
        int len = splitted.length; 
        
        for(int x=0;x<len;x++) {
            if(x == len-1) System.out.println(splitted[x]);
            else System.out.print(splitted[x] + ", ");
        }
    }

    boolean checkUniqueID(String id) {
        for(var i : employees) {
            if(i.getId().equals(id)) return false;
        }
        return true;
    }

    void insertData() {
        clearScreen();
        System.out.println("Insert Employee Data");
        System.out.println("====================");
        String name, gender, position, id;
        int salary;

        //check unique id
        do {
            id = randomID();
        } while(!checkUniqueID(id));

        do {
            System.out.print("Input name [>= 3]: "); name = sc.nextLine();
        } while(name.length() < 3);
        
        do {
            System.out.print("Input Gender [Laki-laki | Perempuan] (Case Sensitive): "); gender = sc.nextLine();
        } while(!gender.equals("Laki-laki") && !gender.equals("Perempuan"));
        
        do {
            System.out.print("Input Position [Manager | Supervisor | Admin] (Case Sensitive): "); position = sc.nextLine();
        } while(!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin"));


        if(position.equals("Manager")) {
            salary = 8_000_000;
            employees.add(new Manager(id, name, gender, position, salary));
        } else if(position.equals("Supervisor")) {
            salary = 6_000_000;
            employees.add(new Supervisor(id, name, gender, position, salary));
        } else if(position.equals("Admin")) {
            salary = 4_000_000;
            employees.add(new Admin(id, name, gender, position, salary));
        }
        
        System.out.println("Successfully add a new Employee with id " + id);
        
        //check the position
        if(checkEmployeePosition(position)) {
            //update the salary with this position
            updateEmployeeSalary(id, position);
        }
        enterToContinue();
    }
    
    void sortData() {
        employees.sort(new Comparator<>() {
            public int compare(Employee a, Employee b) {
                return a.getName().compareTo(b.getName());
            }
        });
    }
    
    void viewData(int printer) {
        clearScreen();        
        System.out.println("View Employee Data");
        System.out.println("====================");

        if(employees.isEmpty()) {
            System.out.println("There's no employee!");
            enterToContinue();
            return;
        }

        sortData();
        int counter = 0;
        System.out.println("No | id\t\t | name\t\t | Gender\t | Position\t | Salary\t");
        for(var i : employees) {
            System.out.printf("%d. %-10s %-15s %-15s %-10s %d\n", ++counter, i.getId(), i.getName(), i.getGender(), i.getPosition(), i.getSalary());
        }
        
        if(printer == 0) enterToContinue();
    }
    
    void enterToContinue() {
        System.out.println("Enter to Continue...");
        sc.nextLine();
    }

    void updateData() {
        clearScreen();
        System.out.println("Update Employee Data");
        System.out.println("====================");
        
        if(employees.isEmpty()) {
            System.out.println("There's no employee!");
            enterToContinue();
            return;
        }

        viewData(1);
        System.out.println("\n");
        int choose = 0;
        System.out.println("Enter 0 to skip!");
        do {
            System.out.printf("Input number to be deleted [1..%d]: ", employees.size());
            try {
                choose = sc.nextInt();
            } catch (Exception e) {
                choose = -1;
            }
            sc.nextLine();
        } while(choose < 0 || choose > employees.size());

        if(choose == 0) return;

        //indexing
        choose--;

        //store previous employee data
        String id = employees.get(choose).getId();
        String name = employees.get(choose).getName();
        String gender = employees.get(choose).getGender();
        String position = employees.get(choose).getPosition();
        int salary = employees.get(choose).getSalary();

        //fields for new updated data
        String newName, newGender, newPosition;
        
        //prompt user to input new data
        System.out.println("Enter 0 to skip!");
        do {
            System.out.print("Input name [>= 3]: "); newName = sc.nextLine();
            if(newName.equals("0")) break;
        } while(newName.length() < 3);
        
        do {
            System.out.print("Input Gender [Laki-laki | Perempuan] (Case Sensitive): "); newGender = sc.nextLine();
            if(newGender.equals("0")) break;
        } while(!newGender.equals("Laki-laki") && !newGender.equals("Perempuan"));
        
        do {
            System.out.print("Input Position [Manager | Supervisor | Admin] (Case Sensitive): "); newPosition = sc.nextLine();
            if(newPosition.equals("0")) break;
        } while(!newPosition.equals("Manager") && !newPosition.equals("Supervisor") && !newPosition.equals("Admin"));

        //update the checker to false 
        boolean isUpdatedPosition = false;
        
        //checking skipped update
        if(!newName.equals("0")) name = newName;
        if(!newGender.equals("0")) gender = newGender;
        if(!newPosition.equals("0")) {
            position = newPosition;
            
            //delete employee with previous position
            employees.remove(choose);
            
            //add a employee and update salary to new position
            if(position.equals("Manager")) {
                salary = 8_000_000;
                employees.add(new Manager(id, name, gender, position, salary));
            } else if(position.equals("Supervisor")) {
                salary = 6_000_000;
                employees.add(new Supervisor(id, name, gender, position, salary));
            } else if(position.equals("Admin")) {
                salary = 4_000_000;
                employees.add(new Admin(id, name, gender, position, salary));
            }
            
            isUpdatedPosition = true;

            //check the position
            if(checkEmployeePosition(position)) {
                //update the salary with this position
                updateEmployeeSalary(id, position);
            }
            
        }
        
        if(!isUpdatedPosition) {
            //update curr data
            if(position.equals("Manager")) employees.set(choose, new Manager(id, name, gender, position, salary));
            else if(position.equals("Supervisor")) employees.set(choose, new Supervisor(id, name, gender, position, salary));
            else if(position.equals("Admin")) employees.set(choose, new Admin(id, name, gender, position, salary));
        }

        System.out.println("Employee with id " + id + " has been updated!");
        enterToContinue();
    }
    
    void deleteData() {
        clearScreen();
        System.out.println("Delete Employee Data");
        System.out.println("====================");
        
        if(employees.isEmpty()) {
            System.out.println("There's no employee!");
            enterToContinue();
            return;
        }
        
        viewData(1);
        System.out.println("\n");
        int choose = -1;
        System.out.println("Enter 0 to skip!");
        do {
            System.out.printf("Input number to be deleted [1..%d]: ", employees.size());
            try {
                choose = sc.nextInt();
            } catch (Exception e) {
                choose = -1;
            }
            sc.nextLine();
        } while(choose < 0 || choose > employees.size());

        if(choose == 0) return;

        //indexing
        choose--;

        System.out.println(employees.get(choose).getName() + " with id " + employees.get(choose).getId() + " has been deleted!");
        employees.remove(choose);

        enterToContinue();
    }
    
    public Main() {
        int choose = 0;
        do {
            clearScreen();
            menu();

            try {
                choose = sc.nextInt();
            } catch (Exception e) {
                
                choose = 0;
            }
            sc.nextLine();

            if(choose == 1) insertData();
            else if(choose == 2) viewData(0);
            else if(choose == 3) updateData();
            else if(choose == 4) deleteData();
            else if(choose < 0 || choose > 5){
                System.out.println("Please input number between 1 to 5!");
                sc.nextLine();
            }
        } while(choose != 5);
        System.out.println("Thanks for using this program!");
    }
}
