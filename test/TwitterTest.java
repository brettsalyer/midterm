import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class TwitterTest {
	int USERS = 456631;
	ArrayList<TwitterUser> TEST_DATA = new ArrayList<TwitterUser>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{add(new TwitterUser(1)); add(new TwitterUser(2)); add(new TwitterUser(3));}}; 
	
	@Test
	public void testLoad() {
		TwitterUserManagerImpl impl = new TwitterUserManagerImpl();
		
		int numUsers = impl.loadTwitterData();
		
		Assert.assertEquals(USERS, numUsers);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testGetNeighborHood() {
		TwitterUserManagerImpl impl = new TwitterUserManagerImpl();
		ArrayList<TwitterUser> neighborhood = new ArrayList<>();
		HashSet<TwitterUser> noDupes;
		impl.loadTwitterData();
		
		//Chose some random ID (10) and depth of 3
		neighborhood = impl.getNeighborhood(impl.getUsers().get(10), 3);
		//Initialize noDupes with neighborhood list, noDUpes will filter out copies
		noDupes = new HashSet(neighborhood);
		
		//If the original matches the filtered, then there were no duplicates
		Assert.assertTrue(neighborhood.containsAll(noDupes));
	}
	
	@Test
	public void testGetUserId() {
		TwitterUser test = new TwitterUser(12345);
		int id = test.getUserId();
		
		Assert.assertEquals(12345, id);
	}
	
	@Test
	public void testSetUserId() {
		TwitterUser test = new TwitterUser();
		test.setUserId(100);
		
		Assert.assertEquals(100, test.getUserId());
	}
	
	@Test
	public void testGetSetFollowing() {
		TwitterUser user = new TwitterUser(0);
		ArrayList<TwitterUser> actual = new ArrayList<>(TEST_DATA);
		
		//Test set following
		user.setFollowing(actual);
		//Test get following
		Assert.assertEquals(user.getFollowing(), actual);	
	}
	
	@Test
	public void testAddFollowing() {
		TwitterUser user = new TwitterUser(0);
		ArrayList<TwitterUser> actual = new ArrayList<>();
		
		TwitterUser test1 = new TwitterUser(1);
		TwitterUser test2 = new TwitterUser(2);
		TwitterUser test3 = new TwitterUser(3);
		
		actual.add(test1);
		actual.add(test2);
		actual.add(test3);

		user.addFollowing(test1);
		user.addFollowing(test2);
		user.addFollowing(test3);

		ArrayList<TwitterUser> expected = new ArrayList<>(user.getFollowing());
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCompareTo() {
		TwitterUser test1 = new TwitterUser(1);
		TwitterUser test2 = new TwitterUser(2);
		TwitterUser test4 = new TwitterUser(1);
		
		//Should return 1 if test2 ID is higher than test 1
		Assert.assertTrue(test1.compareTo(test2) == 1);
		//Should return -1 if test1 is lower than test 2 
		Assert.assertTrue(test2.compareTo(test1) == -1);
		//Should return 0 if their IDs are the same
		Assert.assertTrue(test1.compareTo(test4) == -0);

	}
	
	@Test
	public void testDeepClone() {
		//I think I did this correctly?
		
		//Add two test users
		TwitterUser test1 = new TwitterUser(1);
		TwitterUser test2 = new TwitterUser(5);
		
		//Set the following list to our test_data
		test1.setFollowing(TEST_DATA);
		try {
			//Clone test1 into test2, they should not have the same following list
			test2.equals(test1.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//To make sure it's a deep copy and their lists are separate from each other, add a new item to the cloned object's list
		test2.addFollowing(new TwitterUser(999));
		
		//If they are seperate, they should not be equal and a sucessful deep copy has been achieved
		Assert.assertNotEquals(test1, test2);
	}
}
