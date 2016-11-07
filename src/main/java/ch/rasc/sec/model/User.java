package ch.rasc.sec.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.jpa.criteria.ValueHandlerFactory;
import org.hibernate.jpa.criteria.predicate.BooleanAssertionPredicate;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractPersistable<Long> implements UserDetails {

	private static final long serialVersionUID = 1L;
	private static final int MAX_TRIES = 3;

	@Column(nullable = false, unique = true)
	@NotBlank
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	private String secret;

	@Column
	private Integer tries;

	@Column(unique = true)
	private String authSessionId;

	public User(String login, String password, Set<GrantedAuthority> authorities, Integer tries) {
		this.email = login;
		this.password = password;
		this.tries = tries;
		//this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<>();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return (tries < MAX_TRIES);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
