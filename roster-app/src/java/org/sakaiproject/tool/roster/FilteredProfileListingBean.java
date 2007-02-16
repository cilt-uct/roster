/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2007 The Sakai Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/
package org.sakaiproject.tool.roster;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.sakaiproject.api.app.roster.Participant;
import org.sakaiproject.coursemanagement.api.EnrollmentSet;

public class FilteredProfileListingBean extends InitializableBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected ServicesBean services;
	public void setServices(ServicesBean services) {
		this.services = services;
	}

	protected RosterPreferences prefs;
	public void setPrefs(RosterPreferences prefs) {
		this.prefs = prefs;
	}

	protected String statusFilter;
	protected String searchFilter;
	protected String sectionFilter;


	public List<Participant> getParticipants() {
		// TODO Filter the participants
		return services.rosterManager.getRoster();
	}

	/**
	 * Gets a Map of roles to the number of users in the site with those roles.
	 * @return
	 */
	public Map<String, Integer> getRoleCounts() {
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for(Iterator<Participant> iter = getParticipants().iterator(); iter.hasNext();) {
			Participant participant = iter.next();
			String key = participant.getRoleTitle();
			if(map.containsKey(key)) {
				Integer prevCount = map.get(key);
				map.put(key, ++prevCount);
			} else {
				map.put(key, 1);
			}
		}
		return map;
	}

	/**
	 * We display enrollment details when there is a single EnrollmentSet associated
	 * with a site, or when multiple EnrollmentSets are children of cross listed
	 * CourseOfferings.
	 * 
	 * @return
	 */
	public boolean isDisplayEnrollmentDetails() {
		Set<EnrollmentSet> officialEnrollmentSets = services.rosterManager.getOfficialEnrollmentSetsInSite();
		int count = officialEnrollmentSets.size();
		if(count == 0) return false;
		if(count == 1) return true;

		// TODO Deal with cross listings.  Multiple cross listed courses should still show enrollment details
		return false;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	public String getSectionFilter() {
		return sectionFilter;
	}

	public void setSectionFilter(String sectionFilter) {
		this.sectionFilter = sectionFilter;
	}

	public String getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}
}
