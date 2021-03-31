import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class DeadlockAlgorithm
{
    static Boolean[] copyb(Boolean[] a) {
        Boolean[] b = new Boolean[a.length];
        for(int c=0; c < a.length; c++)
            b[c] = a[c];
        return b;
    }

    static Integer[] copyv(Integer[] a) {
        Integer[] b = new Integer[a.length];
        for(int c=0; c < a.length; c++)
            b[c] = a[c];
        return b;
    }

    static Integer[][] copym(Integer[][] a) {
        Integer[][] b = new Integer[a.length][a[0].length];
        for(int l=0; l < a.length; l++)
            for(int c=0; c < a[0].length; c++)
                b[l][c] = a[l][c];
        return b;
    }

    static void showMatrix(Integer mOne[][], Integer mTwo[][], Integer mThree[][])
    {
        System.out.printf("A - Resources allocated\nM - Amount maximum resource requested\nD - Deallocated resource\nN - Need to resourse\n\n");

        for (int l = 0; l < mOne.length; l++)
        {  
            System.out.printf("P%d - (A) ", l);
            for (int c = 0; c < mOne[0].length; c++) System.out.print(mOne[l][c] + " ");

            System.out.printf("(M) ");
            for (int c = 0; c < mTwo[0].length; c++) System.out.print(mTwo[l][c] + " ");

            System.out.println();
        }

        for (int l = 0; l < mThree.length; l++)  
        {  
            System.out.printf("\nP%d - (N) ", l);
            for (int c = 0; c < mThree[0].length; c++) System.out.print(mThree[l][c] + " ");
        }

        System.out.println();
    }

    static Boolean safety(Integer[][] allocAtMom, Integer[][] maxRecNeed, Integer[] available, ArrayList<Integer> sequence, Boolean[] finish, Integer[][] necessity)
    {
        Boolean desalloc = true;
        Integer cont = 0;

        while (cont < allocAtMom.length)
        {
            for (int p = 0; p < allocAtMom.length; p++)
            {
                if (finish[p] == false)
                {
                    for (int r = 0; r < maxRecNeed[0].length; r++)
                    {
                        if(necessity[p][r] > available[r])
                        {
                            desalloc = false;
                            break;
                        }
                        desalloc = true;
                    }
                    
                    if (desalloc)
                    {
                        for (int r = 0; r < maxRecNeed[0].length; r++) {
                            available[r] += allocAtMom[p][r];
                        }
                        finish[p] = true;
                        sequence.add(p);
                    }
                }
            }
            cont++;
        }

        if (sequence.size() == allocAtMom.length) {
            System.out.printf("\n- safe sequence: ");
            for (cont=0; cont < allocAtMom.length; cont++) 
                System.out.printf("P%d ", sequence.get(cont));
            System.out.println();
            return true;
        }
        return false;
    }
    
    static Boolean avoid(Integer[] allocAtMom, Integer[] maxRecNeed, Integer[] available, Integer[] necessity, Integer p)
    {
        for (int r = 0; r < maxRecNeed.length; r++)
        {
            if(maxRecNeed[r] > necessity[r])
            {
                System.out.printf("Error: P%d had request exceeded\n", p);
                return false;
            }
        }
        
        for (int r = 0; r < maxRecNeed.length; r++)
        {
            if(maxRecNeed[r] > available[r])
            {
                System.out.printf("Error: P%d had requested resources unavailable\n", p);
                return false;
            }
        }

        System.out.printf("- alloc: P%d (A) ", p);
        for (int r = 0; r < maxRecNeed.length; r++)
        {
            available[r] -= maxRecNeed[r];
            allocAtMom[r] += maxRecNeed[r];
            necessity[r] -= maxRecNeed[r];
            System.out.printf("%d ", allocAtMom[r]);
        }
        System.out.println();
        return true;
    }

    static Boolean detection(Integer[][] allocAtMom, Integer[][] maxRecNeed, Integer[] available, ArrayList<Integer> sequence, Boolean[] finish, Integer[][] necessity)
    {
        Boolean desalloc = true;
        Integer cont = 0;

        while (cont < allocAtMom.length)
        {
            for (int p = 0; p < allocAtMom.length; p++)
            {
                if (finish[p] == false)
                {
                    for (int r = 0; r < maxRecNeed[0].length; r++)
                    {
                        if(maxRecNeed[p][r] > available[r])
                        {
                            desalloc = false;
                            break;
                        }
                        desalloc = true;
                    }
                    
                    if (desalloc)
                    {
                        // System.out.printf("- dealloc: P%d ", p);
                        for (int r = 0; r < maxRecNeed[0].length; r++)
                        {
                            available[r] += allocAtMom[p][r];
                            allocAtMom[p][r] = 0;
                            // System.out.printf("%d ", available[r]);
                        }
                        // System.out.println();
                        finish[p] = true;
                        sequence.add(p);
                    }
                }
            }
            cont++;
        }

        if (sequence.size() != allocAtMom.length) {
            System.out.printf("- deadlocked: ");
            for (cont=0; cont < allocAtMom.length; cont++) 
                if(!sequence.contains(cont))
                    System.out.printf("P%d ", cont);
            System.out.println();
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader file = Files.newBufferedReader(Paths.get(args[0])); // file entried
        Integer amProc = Integer.parseInt(args[1]); // amount processes
        Integer amRec = Integer.parseInt(args[2]); // amount recourses 

        String aux[] = args[3].split(" ");
        Integer available[] = new Integer[aux.length];
        for (int i=0; i < aux.length; i++) available[i] = Integer.parseInt(aux[i]);

        Integer allocAtMom[][] = new Integer[amProc][amRec];
        Integer maxRecNeed[][] = new Integer[amProc][amRec];
        Integer necessity[][] = new Integer[amProc][amRec];
        Boolean finishedProc[] = new Boolean[amProc];
        ArrayList<Integer> sequence = new ArrayList<Integer>(amProc);

        try {
            String data[], line;
            int p = 0;
            while ((line = file.readLine()) != null)
            {
                data = line.split(" ");
                for (int r = 0; r < amRec; r++)
                {
                    allocAtMom[p][r] = Integer.parseInt(data[r]);
                    maxRecNeed[p][r] = Integer.parseInt(data[r+amRec]);
                    necessity[p][r] = maxRecNeed[p][r] - allocAtMom[p][r];
                }
                finishedProc[p] = false;
                p++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            showMatrix(allocAtMom, maxRecNeed, necessity);
            file.close();
        }

        System.out.println("\n=> Safety Algorithm:");

        if (safety(copym(allocAtMom), copym(maxRecNeed), copyv(available), sequence, copyb(finishedProc), copym(necessity))) {
            System.out.println("Status: SAFE");
        } else {
            System.out.println("Status: UNSAFE");
        }
        System.out.println("\n=> Avoid Algorithm: P2 request ");
        avoid(copyv(allocAtMom[2]), copyv(maxRecNeed[2]), copyv(available), copyv(necessity[2]), 2);
        
        System.out.println("\n=> Detection Algorithm: without Avoid P2 requestion");
        sequence.clear();
        System.out.println("P2 request 1 resource C\n");
        maxRecNeed[2][2] = 1;

        if (detection(copym(allocAtMom), copym(maxRecNeed), copyv(available), sequence, copyb(finishedProc), copym(necessity))) {
            System.out.println("Status: DEADLOCK");
        }
        
        // avoid(Integer[] allocAtMom, Integer[] maxRecNeed, Integer[] available, Integer[] necessity, Integer p)
        
    }
}
