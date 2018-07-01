import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class TwitterUserManagerImpl implements TwitterUserManager {
		
		static final String PATH = System.getProperty("user.home") + File.separator + "sinclair" + File.separator;
		static final String FILE_NAME = "twitter_data.txt";
		
		//Default constructor
		public TwitterUserManagerImpl() {
			
		}
		
		@Override
		public int loadTwitterData() {
			TwitterUser twitter = new TwitterUser();
			
			// uses try-with-resources syntax to ensure the stream is closed at the end
			try (Stream<String> stream = Files.lines(Paths.get(PATH + FILE_NAME))) {
			 stream.forEach(line -> { // <- lambda expression will contain each line read from the file
				 // Assuming the data looks like this: “0 5”
				 // tokenizes the string using a space character to separate each token in the string	
				 StringTokenizer tokenizer = new StringTokenizer(line, " ");
				 int followingId = 0;
				 int followedId = 0;
				 
				// hasMoreTokens returns true if there are tokens left to be read
				if (tokenizer.hasMoreTokens()) {
				// would grab the 0 from the input line, which is the follower ID, and converts it to an int
				followingId = Integer.parseInt(tokenizer.nextToken().trim());
				}
				if (tokenizer.hasMoreElements()) {
				// would grab the 5 from the input line, which is the user being followed, and converts it to an int
				followedId = Integer.parseInt(tokenizer.nextToken().trim());
				}
									
				if(twitter.getUsers().containsKey(followingId)) {
					//Do not create a new TwitterUser, rather check and see if the follower in this read line is following the next user read in
					if(twitter.getUsers().get(followingId).getFollowing().containsKey(followedId)) {
						//Do not add this follower, as he is already following this person.
					}else {
						//Add this follower, because user is not already following this user
						twitter.getUsers().get(followingId).addFollowing(new TwitterUser(followedId));
					}

				}else {
					//Create this twitter user and add him to a list of users in the TwitterUser class
					TwitterUser newUser = new TwitterUser(followedId);
					twitter.addUser(newUser);
					
					if(newUser.getFollowing().containsKey(followedId)) {
						//Do nothing
					}else {
						//Add this user to the following list
						newUser.addFollowing(new TwitterUser(followedId));
					}
					
				}
							
			 });
			 
			} catch (IOException exception) {
			 exception.printStackTrace();
			}
			
					
			 return twitter.getUsers().size();
	
		}
		@Override
		public List<TwitterUser> getNeighborhood(TwitterUser user, int maximumDepth) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
}
