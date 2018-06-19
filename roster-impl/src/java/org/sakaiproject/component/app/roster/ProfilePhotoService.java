/**********************************************************************************
*
* $Id$
*
***********************************************************************************
*
 * Copyright (c) 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*
**********************************************************************************/

package org.sakaiproject.component.app.roster;

import org.sakaiproject.api.app.profile.ProfileManager;
import org.sakaiproject.api.app.roster.PhotoService;

import lombok.extern.slf4j.Slf4j;

/**
 * By default, roster photos come from the Profile service.
 */
@Slf4j
public class ProfilePhotoService implements PhotoService {
	
	private ProfileManager profileManager;

	/* (non-Javadoc)
	 * @see org.sakaiproject.api.app.roster.PhotoService#getPhotoAsByteArray(java.lang.String)
	 */
	public byte[] getPhotoAsByteArray(String userId, boolean hasSitePermission) {
		if (log.isDebugEnabled()) log.debug("getPhotoAsByteArray for " + userId + " from " + profileManager);
		if (profileManager != null) {
			return profileManager.getInstitutionalPhotoByUserId(userId, hasSitePermission);			
		} else {
			return null;
		}
	}

	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

}
