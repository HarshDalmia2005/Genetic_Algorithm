import java.util.*;
public class Main
  {
    public static String GENES="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890,.!? ";

    public static String TARGET="Harsh Dalmia";

    public static int population_size=100;

    public static double mutation_rate=0.01;

    public static Random random=new Random();

    public static char getRandomChar()
    {
      return GENES.charAt(random.nextInt(GENES.length()));
    }

    public static String getRandomString()
    {
      int len=TARGET.length();
      StringBuilder sb=new StringBuilder();
      for(int i=0;i<len;i++)
        {
           sb.append(getRandomChar());
        }

      return sb.toString();
    }

    public static String crossover(String parent1,String parent2)
    {
      int mid=random.nextInt(parent1.length());
      StringBuilder child=new StringBuilder();
      for(int i=0;i<parent1.length();i++)
        {
          if(i>mid)
          {
            child.append(parent1.charAt(i));
          }
          else
          {
            child.append(parent2.charAt(i));
          }
        }
      return child.toString();
    }

    public static String mutate(String child)
    {
      int len=child.length();
      StringBuilder mutated=new StringBuilder(child);

      for(int i=0;i<len;i++)
        {
          if(random.nextDouble()<mutation_rate)
          {
            mutated.setCharAt(i,getRandomChar());
          }
        }
      return mutated.toString();
    }


    public static double fitness(String indv)
    {
      int len=indv.length();
      int score=0;
      for(int i=0;i<len;i++)
        {
          if(indv.charAt(i)==TARGET.charAt(i))
          {
            score++;
          }
        }
      return (double)score/TARGET.length();
    }

    public static String selectParent(String population[],double fitness[],int size)
    {

      String tournament[]=new String[size];
      double tournamentFitness[]=new double[size];

      for(int i=0;i<size;i++)
        {
          int randomIndex = random.nextInt(population_size);
          tournament[i] = population[randomIndex];
          tournamentFitness[i] = fitness[randomIndex];
        }

      int bestIndex=0;
      double bestfitness=tournamentFitness[0];

      for(int i=0;i<size;i++)
        {
          if (tournamentFitness[i] > bestfitness) {
              bestfitness = tournamentFitness[i];
              bestIndex = i;
          }
        }
      return tournament[bestIndex];

    }

    public static void main(String args[])
    {
      Scanner sc=new Scanner(System.in);
      String population[]=new String[population_size];
      double fitness[]=new double[population_size];

      for(int i=0;i<population_size;i++)
        {
          population[i]=getRandomString();
        }

      int generation=1;

      while(true)
        {


          for(int i=0;i<population_size;i++)
            {
              fitness[i]=fitness(population[i]);
            }

          for(int i=0;i<population_size;i++)
            {
              if(fitness[i]==1.0)
              {
                System.out.println();
                  System.out.println("*****************");
                  System.out.println("   Generation: " + generation);
                  System.out.println("*****************");
                  int j=0;
                  for (String individual : population) {
                      System.out.println(individual);
                  }

                    System.out.println("Solution found in generation " + generation + ": " + population[i]);
                    return;
                }
              }


          //new Generation

          String[] newPopulation=new String[population_size];

          for(int i=0;i<population_size;i++)
            {

              String parent1=selectParent(population,fitness,10);
              String parent2=selectParent(population,fitness,10);

              String child=crossover(parent1, parent2);
              child=mutate(child);

              newPopulation[i]=child;
            }

          System.out.println();
          System.out.println("*****************");
          System.out.println("   Generation: " + generation);
          System.out.println("*****************");
          for (String individual : newPopulation) {
              System.out.println(individual);
          }

          population=newPopulation;
          generation++;

            }
        }
    }
