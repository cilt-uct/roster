<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
 <% response.setContentType("text/html; charset=UTF-8"); %>
 <f:view>
	<sakai:view_container title="#{msgs.profile_site_roster}">
		<sakai:view_content>
		 <h:form>
		    <sakai:view_title  value="#{msgs.profile_user_profile}"/>
			<h:panelGrid columns="1" border="0" >
			<%-- TODO: should the image sizes be predetermine for this view? --%>
				<h:graphicImage alt="#{msgs.profile_no_picture_available}"  value="#{msgs.img_unavail}" styleClass="rosterImage"/>					
			</h:panelGrid>			
			<h4><h:outputText  value="#{msgs.profile_public_information}"/>	</h4>			 
				<p class="shorttext">
			 	<sakai:panel_edit >	
					<h:outputLabel style ="shorttext" value="#{msgs.profile_first_name}"/>
					<h:outputText value="#{profileBean.participant.profile.firstName}"/> 
					<h:outputLabel value="#{msgs.profile_last_name}"/>
					<h:outputText value="#{profileBean.participant.profile.lastName}"/>
					<h:outputLabel value="#{msgs.profile_nick_name}"/>				
					<h:outputText value="#{profileBean.participant.profile.nickName}"/>
					<h:outputLabel value="#{msgs.profile_position}"/> 				
					<h:outputText value="#{profileBean.participant.profile.position}"/> 
					<h:outputLabel value="#{msgs.profile_department}"/>
					<h:outputText value="#{profileBean.participant.profile.department}"/> 
					<h:outputLabel value="#{msgs.profile_school}"/>
					<h:outputText value="#{profileBean.participant.profile.school}"/>
					<h:outputLabel value="#{msgs.profile_room}"/>
					<h:outputText value="#{profileBean.participant.profile.room}"/> 
				</sakai:panel_edit>
				</p>
			 <h4><h:outputText  value="#{msgs.profile_personal_information}"/></h4>
		  		<jsp:include page="personalInfoUnavailable.jsp"/>
	 		 
		 	 <h:panelGrid>
			 	<h:commandButton styleClass="active" accesskey="x" id="submit" value="#{msgs.back}" immediate="true" action="overview" />
			 </h:panelGrid>	
		   </h:form>
  		</sakai:view_content>
	</sakai:view_container>
</f:view>

	
