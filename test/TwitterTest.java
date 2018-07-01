import org.junit.Assert;
import org.junit.Test;

public class TwitterTest {

	int USERS = 456631;
	
	@Test
	public void testLoad() {
		TwitterUserManagerImpl impl = new TwitterUserManagerImpl();
		int numUsers = impl.loadTwitterData();
		
		Assert.assertEquals(USERS, numUsers);
	}
}
