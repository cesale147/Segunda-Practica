package pweb.data;

public class LoginDB
{
    private static String User = "user";
    private static String Pass = "123";
    
    public static boolean Validar(String user, String pass ) 
    {           
        if (User.equals(user) && Pass.equals(pass)) 
        {
            return true;
        }
        return false;
    }    
}
