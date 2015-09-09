package org.wso2.appserver.integration.common.clients;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.registry.activities.stub.ActivityAdminServiceStub;
import org.wso2.carbon.registry.activities.stub.RegistryExceptionException;
import org.wso2.carbon.registry.activities.stub.beans.xsd.ActivityBean;

import java.rmi.RemoteException;

public class ActivityAdminServiceClient {
    private static final Log log = LogFactory.getLog(ActivityAdminServiceClient.class);

    private final String serviceName = "ActivityAdminService";
    private ActivityAdminServiceStub activityAdminServiceStub;
    private String endPoint;

    public final static String FILTER_ALL = "All";
    public final static String FILTER_ASSOCIATE_ASPECT = "Associate Aspect";
    public final static String FILTER_RESOURCE_ADDED = "Resource Add";
    public final static String FILTER_RESOURCE_UPDATE = "Resource Update";

    public ActivityAdminServiceClient(String backEndUrl, String sessionCookie) throws AxisFault {
        this.endPoint = backEndUrl + serviceName;
        try {
            activityAdminServiceStub = new ActivityAdminServiceStub(endPoint);
        } catch (AxisFault axisFault) {
            log.error("activityAdminServiceStub Initialization fail " + axisFault.getMessage());
            throw new AxisFault("activityAdminServiceStub Initialization fail ", axisFault);
        }
        AuthenticateStub.authenticateStub(sessionCookie, activityAdminServiceStub);
    }

    public ActivityAdminServiceClient(String backEndUrl, String userName, String password)
            throws AxisFault {
        this.endPoint = backEndUrl + serviceName;
        activityAdminServiceStub = new ActivityAdminServiceStub(endPoint);
        AuthenticateStub.authenticateStub(userName, password, activityAdminServiceStub);
    }

    public ActivityBean getActivities(String sessionCookie, String userName, String resourcePath
            , String fromDate, String toDate, String filter, int page)
            throws RemoteException, RegistryExceptionException {
        return activityAdminServiceStub.getActivities(userName, resourcePath, fromDate, toDate
                , filter, page + "", sessionCookie);
    }

    public ConfigurationContext getConfigurationContext() {
        return activityAdminServiceStub._getServiceClient().getServiceContext().getConfigurationContext();
    }
}