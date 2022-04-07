package tikedu.app.tikedu;

public class UserRepository
{
    private static UserRepository userRepository = null;

    private String accessToken;
    private String name;
    private String usertype;

    public static class UserNotYetCreatedException extends Exception
    {
        //TODO: Make sure this exception class can be empty like this
    }

    private UserRepository(String accessToken)
    {
        this.accessToken = accessToken;
        //TODO: in background thread, get this user's student type and name using accessToken by requesting them from the server
    }

    public static UserRepository getInstance() throws UserNotYetCreatedException
    {
        if(userRepository == null)
        {
           throw new UserNotYetCreatedException();
        }

        return userRepository;
    }

    public static void createUser(String accessToken)
    {
        if(userRepository != null)
        {
            userRepository = new UserRepository(accessToken);
        }
    }

}
