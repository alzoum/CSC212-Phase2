
public class ContactBST<T> {
	private BSTNode<Contact> root,current;
	
	
	 public ContactBST() {
	        this.root = null;
	        this.current = null;
	    }

	 public Contact getCurrentContact() {
	        return (current != null) ? current.getData() : null;
	    }
	 
	 
	    public void insert(Contact contact) {
	        if (!contactExists(contact)) {
	            if (root == null) {
	                root = new BSTNode<>(contact);
	                current = root;
	            } else {
	                insertRec(root, contact);
	            }
	        } else {
	            System.out.println("Contact with this name or phone number already exists.");
	        }
	    }
	
	    private BSTNode<Contact> insertRec(BSTNode<Contact> current, Contact contact) {
	        if (current == null) {
	            return new BSTNode<>(contact);
	        }
	        if (contact.compareTo(current.getData()) < 0) {
	            current.setLeft(insertRec(current.getLeft(), contact));
	        } else if (contact.compareTo(current.getData()) > 0) {
	            current.setRight(insertRec(current.getRight(), contact));
	        }
	        return current;
	    }
	
	    private boolean contactExists(Contact contact) {
	        return findRec(root, contact.getName()) != null || phoneNumberExistsRec(root, contact.getPhoneNumber());
	    }
	   
	    public T find(String name) {
	        current = root;
	        return (T)findRec(current, name);
	    }
	    
	    private Contact findRec(BSTNode<Contact> current, String name) {
	        if (current == null) {
	            return null;
	        }
	        int cmp = name.compareTo(current.getData().getName());
	        if (cmp < 0) {
	            return findRec(current.getLeft(), name);
	        } else if (cmp > 0) {
	            return findRec(current.getRight(), name);
	        } else {
	            return current.getData(); // Found
	        }
	    }
	
	    private boolean phoneNumberExistsRec(BSTNode<Contact> current, String phoneNumber) {
	        if (current == null) {
	            return false;
	        }
	        if (current.getData().getPhoneNumber().equals(phoneNumber)) {
	            return true;
	        }
	        return phoneNumberExistsRec(current.getLeft(), phoneNumber) || phoneNumberExistsRec(current.getRight(), phoneNumber);
	    }
	    
	    
}
