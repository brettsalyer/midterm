import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class TwitterUserManagerImpl implements TwitterUserManager {
		
		static final String PATH = System.getProperty("user.home") + File.separator + "sinclair" + File.separator;
		static final String FILE_NAME = "twitter_data.txt";
		
		private HashMap<Integer, TwitterUser> users;

		//Default constructor
		public TwitterUserManagerImpl() {
			users = new HashMap<>();
		}
		
		@Override
		public int loadTwitterData() {
			
			// uses try-with-resources syntax to ensure the stream is closed at the end
			try (Stream<String> stream = Files.lines(Paths.get(PATH + FILE_NAME))) {
			 stream.forEach(line -> { // <- lambda expression will contain each line read from the file
				 // Assuming the data looks like this: �0 5�
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
				
				//If user exists
				if(users.containsKey(followingId)) {
					//If user is already following user
					if(users.get(followingId).getFollowing().contains(new TwitterUser(followingId))) {
						//Do nothing, as he is already following this person
					}else {
						//Add this person to their following list
						users.get(followingId).addFollowing(new TwitterUser(followedId));
					}
				}
				else {
					users.put(followingId, new TwitterUser(followingId));
				}
				
							
			 });
			 
			} catch (IOException exception) {
			 exception.printStackTrace();
			}
			
					
			 return users.size();
	
		}
		
		@Override
		public ArrayList<TwitterUser> getNeighborhood(TwitterUser tu, int depth){
			ArrayList<TwitterUser> neighbordhood = new ArrayList<>();
			ArrayList<TwitterUser> followers = new ArrayList<>();
			
			//Base case - stop if we've reached depth of 0 starting from 3
			if (depth <= 0) {
				System.out.println("No more users");
			}else{
				//If we haven't reached base case, and following isn't empty
			if(depth > 0 && tu.getFollowing().size() > 0){
				for(TwitterUser user : tu.getFollowing()){
					//add this user
					neighbordhood.add(user);
					//Recursion - get this users following list and decrease the depth
					followers.equals(getNeighborhood(user, depth--));
					//loop through these users and add them to the neighborhood if they aren't there already
					for(TwitterUser follower : followers){
						if(!neighbordhood.contains(follower)){
							neighbordhood.add(follower);
						}
					}
				}
				
			}
			}
			//Return this list
			return neighbordhood;
		}
		
		public HashMap<Integer, TwitterUser> getUsers(){
			return this.users;
		}
		
		
}
