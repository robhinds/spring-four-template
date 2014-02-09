package com.tmm.frm.core.dao;

import java.util.List;

import com.tmm.frm.domain.Profile;

public interface ProfileDAO {

	List<Profile> loadAllProfiles();

	List<Profile> loadProfiles(Long[] ids);
}