
package simulacao_ad;

public class RandomGenerator {
     static long  a = 25214903917L;
     static long m = 2^48;
     static long c = 11;
     
     public static float[] randNumb(int seed){
         float xi = (a*seed + c)%m;
         float ui = xi/m;
         float [] r  = {xi,ui};
         return r;
     } 
}
