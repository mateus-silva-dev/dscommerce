package com.devsuperior.dscommerce.projections;

public interface UserDetailsProjection {
    String getEmail();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
