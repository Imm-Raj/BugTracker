package sample;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) throws PositionNotFoundException {
        testAccountTicketProjectInfo();

    }

    public static void testAccountTicketProjectInfo() throws PositionNotFoundException {
        String[] positionsAcc =  {"Junior Developer", "Analyst", "Tester"};

        Account accCreator = new Account("J123", "H45098tj","James", "Harrison", 2003, 7, 3, positionsAcc );

        Account acc1 = new Account("J1", "1","Mat  ", "haros", 2003, 7, 3, positionsAcc );

        Account acc2 = new Account("j2", "2","Sam", "Jamesh", 2003, 7, 3, positionsAcc );

        ArrayList<Account> dList = new ArrayList<>();
        dList.add(accCreator);
        dList.add(acc1);
        dList.add(acc2);

        AllCreatedProjects pAll = AllCreatedProjects.getInstance();

        try {
            pAll.addProject("Bug trax", "bug tracking system", accCreator, dList);
            pAll.addProject("Snake game", "Sname gaming system", accCreator, dList);
            pAll.addProject("Fifa", "Fifa gaming system", accCreator, dList);
        } catch (AllReadyExistsException e) {
            e.printStackTrace();
        }  catch (EmptyAccountsListException e) {
            e.printStackTrace();
        } catch (CreatorAccountNullException e) {
            e.printStackTrace();
        }

        try {

            pAll.getProjectFromList("0").createTicketForThisProject("Fix this ", "Very important", 3,3, accCreator, dList);
            pAll.getProjectFromList("1").createTicketForThisProject("Fix this ", "Very important", 3,3, accCreator, dList);
        } catch (AllReadyExistsException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("project not found");
        } catch (CreatorAccountNullException e) {
            e.printStackTrace();
        } catch (EmptyAccountsListException e) {
            e.printStackTrace();
        }


        for (Account a : dList) {
            a.accountToString();
        }

        System.out.println();

        System.out.println("J1 is creating a ticket for bug trax");

        System.out.println();

        try {
            pAll.getProjectFromList("0").createTicketForThisProject("Fixthe error pane ", "error pane color is not red", 1, 1, acc1, dList);
        } catch (AllReadyExistsException e) {
            e.printStackTrace();
        } catch (CreatorAccountNullException e) {
            e.printStackTrace();
        } catch (EmptyAccountsListException e) {
            e.printStackTrace();
        }

        for (Account a : dList) {
            a.accountToString();
        }

        System.out.println();
        System.out.println("Removing a ticket");

        System.out.println();

        try {
          //  pAll.getProjectFromList("0").removeTicketFromThisProject("0 - 0");
        } catch (IllegalArgumentException e) {
            System.out.println("ticket not found");
        }
        for (Account a : dList) {
            a.accountToString();
        }

        System.out.println();
        System.out.println("Removing a project: 0");

        System.out.println();

        try {
            pAll.removeProjectFromList("0");
        } catch (IllegalArgumentException e) {
            System.out.println("project not found");
        }

        for (Account a : dList) {
            a.accountToString();
        }

    }

    public static void testRemovingOfProjects() throws PositionNotFoundException {
        AllCreatedProjects pAll = AllCreatedProjects.getInstance();

        String[] positionsAcc =  {"Junior Developer", "Analyst", "Tester"};


        Account acc = new Account("J123", "H45098tj","James", "Harrison", 2003, 7, 3, positionsAcc );

        ArrayList<Account> dList = new ArrayList<>();
        dList.add(acc);

        try {
            pAll.addProject("Bug trax", "bug tracking system", acc, dList);
            pAll.addProject("Snake game", "Sname gaming system", acc, dList);
            pAll.addProject("Fifa", "Fifa gaming system", acc, dList);
        } catch (AllReadyExistsException e) {
            e.printStackTrace();
        } catch (EmptyAccountsListException e) {
            e.printStackTrace();
        } catch (CreatorAccountNullException e) {
            e.printStackTrace();
        }

        for (Project p : pAll.getProjectsList()) {
            try {
                p.createTicketForThisProject("Fix errorPane", "Error pane colour combination is faulty", 3, 3, acc, dList);
                p.createTicketForThisProject("Add Account 1", "Code it", 2,1, acc, dList);
                p.createTicketForThisProject("Add Account 2", "Code it", 2,2, acc,  dList);
                p.createTicketForThisProject("Add Account 3", "Code it", 2,2, acc, dList);
                p.createTicketForThisProject("Add Account 4", "Code it", 2,2, acc, dList);
            } catch (AllReadyExistsException e) {
                e.printStackTrace();
            } catch (EmptyAccountsListException e) {
                e.printStackTrace();
            } catch (CreatorAccountNullException e) {
                e.printStackTrace();
            }
        }

        pAll.printAllProjectsInfo();



        System.out.println("Removing Projects");
        System.out.println();
        System.out.println();

        try {

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        pAll.printAllProjectsInfo();

    }


    public static void testRemovingOfTickets() throws PositionNotFoundException {

        AllCreatedProjects pAll = AllCreatedProjects.getInstance();

        String[] positionsAcc =  {"Junior Developer", "Analyst", "Tester"};


        Account acc = new Account("J123", "H45098tj","James", "Harrison", 2003, 7, 3, positionsAcc );

        ArrayList<Account> dList = new ArrayList<>();
        dList.add(acc);

        try {
            pAll.addProject("Bug trax", "bug tracking system", acc, dList);
            pAll.addProject("Snake game", "Sname gaming system", acc, dList);
            pAll.addProject("Fifa", "Fifa gaming system", acc, dList);
        } catch (AllReadyExistsException e) {
            e.printStackTrace();
        } catch (EmptyAccountsListException e) {
            e.printStackTrace();
        } catch (CreatorAccountNullException e) {
            e.printStackTrace();
        }

        for (Project p : pAll.getProjectsList()) {
            try {
                p.createTicketForThisProject("Fix errorPane", "Error pane colour combination is faulty", 3, 3, acc, dList);
                p.createTicketForThisProject("Add Account 1", "Code it", 2,1, acc, dList);
                p.createTicketForThisProject("Add Account 2", "Code it", 2,2, acc, dList);
                p.createTicketForThisProject("Add Account 3", "Code it", 2,2, acc, dList);
                p.createTicketForThisProject("Add Account 4", "Code it", 2,2, acc, dList);
            } catch (AllReadyExistsException e) {
                e.printStackTrace();
            } catch (EmptyAccountsListException e) {
                e.printStackTrace();
            } catch (CreatorAccountNullException e) {
                e.printStackTrace();
            }
        }

        pAll.printAllProjectsInfo();

        Project proj = pAll.getProjectFromList("1");

        System.out.println("Removing tickets");
        System.out.println();
        System.out.println();

        try {
            proj.removeTicketFromThisProject("1 - 4");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        pAll.printAllProjectsInfo();

    }

    public static void testChangingOfStatusORPriorityOfTickets() throws PositionNotFoundException {
        AllCreatedProjects pAll = AllCreatedProjects.getInstance();

        String[] positionsAcc =  {"Junior Developer", "Analyst", "Tester"};


        Account acc = new Account("J123", "H45098tj","James", "Harrison", 2003, 7, 3, positionsAcc );

        ArrayList<Account> dList = new ArrayList<>();
        dList.add(acc);

        try {
            pAll.addProject("Bug trax", "bug tracking system", acc, dList);
            pAll.addProject("Snake game", "Sname gaming system", acc, dList);
            pAll.addProject("Fifa", "Fifa gaming system", acc, dList);
        } catch (AllReadyExistsException e) {
            e.printStackTrace();
        } catch (EmptyAccountsListException e) {
            e.printStackTrace();
        } catch (CreatorAccountNullException e) {
            e.printStackTrace();
        }

        for (Project p : pAll.getProjectsList()) {
            try {
                p.createTicketForThisProject("Fix errorPane", "Error pane colour combination is faulty", 3, 3, acc, dList);
                p.createTicketForThisProject("Add Account Pane", "Code it", 2,1, acc, dList);
                p.createTicketForThisProject("Add Account Pane", "Code it", 2,2, acc, dList);
                p.createTicketForThisProject("Add Account Pane", "Code it", 2,2, acc, dList);
                p.createTicketForThisProject("Add Account Pane", "Code it", 2,2, acc, dList);
            } catch (AllReadyExistsException e) {
                e.printStackTrace();
            } catch (EmptyAccountsListException e) {
                e.printStackTrace();
            } catch (CreatorAccountNullException e) {
                e.printStackTrace();
            }
        }

        pAll.printAllProjectsInfo();

        System.out.println("Changes here");
        System.out.println();
        System.out.println();

        for (Project p : pAll.getProjectsList()) {
            for (Ticket t : p.getListOfTickets()) {
                try {
                    t.changePriority(t.getPriority() + 1);
                } catch (PriorityNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    t.changeStatus(t.getCurrentStatus() + 1);
                } catch (StatusNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        pAll.printAllProjectsInfo();
    }

/*
    public static void testAccountSetup() throws PositionNotFoundException {

        String[] positionsAcc =  {"Junior Developer", "Analyst", "Tester"};

        Account acc = new Account("J123", "H45098tj","James", "Harrison", 2003, 7, 3, positionsAcc );

        System.out.println("UserName " + acc.getUserName());

        System.out.println("All positions: ");

        int counter = 0;
        for (String p : acc.getAllPositions()) {
            System.out.print(p);
            if (counter < acc.getAllPositions().length - 1) {
                System.out.print(", ");
            }
            counter++;
        }

        System.out.println();
        System.out.println();

        String[] newPosAcc = {"Analyst", "Developer"};

        acc.updatePosition(newPosAcc);

        System.out.println("All new positions: ");

        int counter2 = 0;
        for (String p : acc.getAllPositions()) {
            System.out.print(p);
            if (counter2 < acc.getAllPositions().length - 1) {
                System.out.print(", ");
            }
            counter2++;
        }
    }
*/
}
