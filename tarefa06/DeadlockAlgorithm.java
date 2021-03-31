import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/* 
 * -             allocAtMom   maxRecNeed
 * Recourse      A B C        A B C
 * Proc0         3 1 0        7 5 7
 * Proc1         7 0 1        7 1 1
 * Proc2         0 2 2        10 4 3
**/
public class DeadlockAlgorithm
{
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

        System.out.println("\n");
    }

    static Boolean safety(Integer[][] allocAtMom, Integer[][] maxRecNeed, Integer[] available, Boolean[] finish)
    {
        Boolean desalloc = true, safe = true;

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
                } 
                else {
                    System.out.printf("- unsafe: P%d ", p);
                    for (int r = 0; r < available.length; r++)
                    {
                        System.out.printf("%d ", available[r]);
                    }
                    System.out.println();
                    safe = false;
                }
            }
        }

        return safe;
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

    static Boolean detection(Integer[][] allocAtMom, Integer[][] maxRecNeed, Integer[] available, Boolean[] finish, Integer[][] necessity)
    {
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

        if (safety(allocAtMom, maxRecNeed, available, finishedProc)) {
            System.out.println("\nStatus: SAFE");
        } else {
            System.out.println("\nStatus: UNSAFE");
        }
    }
}
