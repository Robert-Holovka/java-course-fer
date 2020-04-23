package hr.fer.zemris.java.hw15.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "BlogUser.getByNick", query = "select u from BlogUser as u where u.nick=:n")
})
@Entity
@Table(name = "blog_users")
public class BlogUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;
	private Collection<BlogEntry> entries = new HashSet<>();

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 30, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length = 20, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(length = 30, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 40, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@OneToMany(mappedBy = "creator")
	public Collection<BlogEntry> getEntries() {
		return entries;
	}

	public void setEntries(Collection<BlogEntry> entries) {
		this.entries = entries;
	}

}
