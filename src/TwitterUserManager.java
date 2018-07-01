import java.util.List;

public interface TwitterUserManager {
	
	int loadTwitterData();
	
	List<TwitterUser> getNeighborhood(TwitterUser user, int maximumDepth);
		
}
