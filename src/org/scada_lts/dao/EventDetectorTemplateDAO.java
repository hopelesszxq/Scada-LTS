package org.scada_lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scada_lts.exception.EventDetectorTemplateExceptionDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.serotonin.mango.vo.event.PointEventDetectorVO;

import br.org.scadabr.vo.eventDetectorTemplate.EventDetectorTemplateVO;

public class EventDetectorTemplateDAO {
	
	private static final Log LOG = LogFactory.getLog(EventDetectorTemplateDAO.class);
	
	//templatesDetectors 
	private final static String  COLUMN_NAME_ID = "id";
	private final static String  COLUMN_NAME_XID = "xid";
	private final static String  COLUMN_NAME_ALIAS = "alias";
	private final static String  COLUMN_NAME_DETECTOR_TYPE = "detectorType";
	private final static String  COLUMN_NAME_ALARM_LEVEL = "alarmLevel";
	private final static String  COLUMN_NAME_STATE_LIMIT = "stateLimit";
	private final static String  COLUMN_NAME_DURATION = "duration";
	private final static String  COLUMN_NAME_DURATION_TYPE = "durationType";
	private final static String  COLUMN_NAME_BINARY_STATE = "binaryState";
	private final static String  COLUMN_NAME_MYLTISTATE_STATE = "multistateState";
	private final static String  COLUMN_NAME_CHANGE_COUNT = "changeCount";
	private final static String  COLUMN_NAME_ALPHANUMERIC_STATE = "alphanumericState";
	private final static String  COLUMN_NAME_WEIGHT = "weight";
	private final static String  COLUMN_NAME_THRESHOLD = "treshold";
	private final static String  COLUMN_NAME_EVENT_DETECTOR_TEMPLATE_ID = "eventDetectorTemplateId";
	
	// @formatter:off
	private static final String DETECTORS_SELECT = " "+
			"select "
				+ COLUMN_NAME_ID + ", "
				+ COLUMN_NAME_XID + ", "
				+ COLUMN_NAME_ALIAS + ", "
				+ COLUMN_NAME_DETECTOR_TYPE + ", "
				+ COLUMN_NAME_ALARM_LEVEL + ", "
				+ COLUMN_NAME_STATE_LIMIT + ", "
				+ COLUMN_NAME_DURATION + ", "
				+ COLUMN_NAME_DURATION_TYPE + ", "
				+ COLUMN_NAME_BINARY_STATE + ", " 
				+ COLUMN_NAME_MYLTISTATE_STATE + ", "
				+ COLUMN_NAME_CHANGE_COUNT + ", "
				+ COLUMN_NAME_ALPHANUMERIC_STATE + ", "
				+ COLUMN_NAME_WEIGHT + ", "
				+ COLUMN_NAME_THRESHOLD + " " 
			+ "from "
				+ "templatesDetectors " 
			+ "where "
				+ COLUMN_NAME_EVENT_DETECTOR_TEMPLATE_ID +"=? " 
			+ "order by id";

	private static final String DETECTORS_INSERT = ""
			+ "insert into templatesDetectors ("
				+ COLUMN_NAME_XID + ", "
				+ COLUMN_NAME_ALIAS + ", "
				+ COLUMN_NAME_DETECTOR_TYPE + ", "
				+ COLUMN_NAME_ALARM_LEVEL + ", "
				+ COLUMN_NAME_STATE_LIMIT + ", "
				+ COLUMN_NAME_DURATION + ", "
				+ COLUMN_NAME_DURATION_TYPE + ", "
				+ COLUMN_NAME_BINARY_STATE + ", " 
				+ COLUMN_NAME_MYLTISTATE_STATE + ", "
				+ COLUMN_NAME_CHANGE_COUNT + ", "
				+ COLUMN_NAME_ALPHANUMERIC_STATE + ", "
				+ COLUMN_NAME_WEIGHT + ", "
				+ COLUMN_NAME_THRESHOLD + ", "
				+ COLUMN_NAME_EVENT_DETECTOR_TEMPLATE_ID + " "
			+") "
			+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	// @formatter:on
	
	//eventDetectorTemplates
	private final static String  COLUMN_NAME_EVENT_DETEC_TEMPL_ID = "id";
	private final static String  COLUMN_NAME_EVENT_DETEC_TEMPL_NAME = "name";
		
	// @formatter:off
	private static final String TEMPLATES_INSERT = ""
			+ "insert into eventDetectorTemplates ("
				+ COLUMN_NAME_EVENT_DETEC_TEMPL_NAME
			+") " 
			+ "values (?)";

	private static final String TEMPLATES_SELECT = ""
			+ "select "
				+ COLUMN_NAME_EVENT_DETEC_TEMPL_ID+", "
				+ COLUMN_NAME_EVENT_DETEC_TEMPL_NAME+" "
			+ "from "
				+ "eventDetectorTemplates ";
			
	// @formatter:on
	
	public EventDetectorTemplateVO getEventDetectorTemplate(int id) {
		
		if (LOG.isTraceEnabled()) {
			LOG.trace("getEventDetectorTemplate(int id) id:"+id);
		}
		
		String templateSelectWhereId = TEMPLATES_SELECT +" where " + COLUMN_NAME_EVENT_DETEC_TEMPL_ID + "=? ";

		@SuppressWarnings({ "unchecked", "rawtypes" })
		EventDetectorTemplateVO eventDetectorTemplateVO = (EventDetectorTemplateVO) DAO.getInstance().getJdbcTemp().queryForObject( templateSelectWhereId, new Object[] { id },  
				new RowMapper() {
					@Override
					public EventDetectorTemplateVO mapRow(ResultSet rs, int rownumber) throws SQLException {
							EventDetectorTemplateVO eventDetectorTempl = new EventDetectorTemplateVO();
							eventDetectorTempl.setId(rs.getInt(COLUMN_NAME_EVENT_DETEC_TEMPL_ID));
							eventDetectorTempl.setName(rs.getString(COLUMN_NAME_EVENT_DETEC_TEMPL_NAME));
							return eventDetectorTempl;
					}
				}
		);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<PointEventDetectorVO> listPointEventDetectorVO = DAO.getInstance().getJdbcTemp().query(DETECTORS_SELECT, new Object[] { eventDetectorTemplateVO.getId() },new RowMapper() {
			@Override
			public PointEventDetectorVO mapRow(ResultSet rs, int rownumber) throws SQLException {
				
					PointEventDetectorVO detector = new PointEventDetectorVO();
						detector.setId(rs.getInt(COLUMN_NAME_ID));
						detector.setXid(rs.getString(COLUMN_NAME_XID));
						detector.setAlias(rs.getString(COLUMN_NAME_ALIAS));
						detector.setDetectorType(rs.getInt(COLUMN_NAME_DETECTOR_TYPE));
						detector.setAlarmLevel(rs.getInt(COLUMN_NAME_ALARM_LEVEL));
						detector.setLimit(rs.getDouble(COLUMN_NAME_STATE_LIMIT));
						detector.setDuration(rs.getInt(COLUMN_NAME_DURATION));
						detector.setDurationType(rs.getInt(COLUMN_NAME_DURATION_TYPE));
						detector.setBinaryState(DAO.charToBool(rs.getString(COLUMN_NAME_BINARY_STATE)));
						detector.setMultistateState(rs.getInt(COLUMN_NAME_MYLTISTATE_STATE));
						detector.setChangeCount(rs.getInt(COLUMN_NAME_CHANGE_COUNT));
						detector.setAlphanumericState(rs.getString(COLUMN_NAME_ALPHANUMERIC_STATE));
						detector.setWeight(rs.getDouble(COLUMN_NAME_WEIGHT));
						return detector;
					}
		});
		
		eventDetectorTemplateVO.setEventDetectors(listPointEventDetectorVO);
		
		return eventDetectorTemplateVO;
	}
	
	public List<EventDetectorTemplateVO> getEventDetectorTemplatesWithoutDetectors() {
		
		if (LOG.isTraceEnabled()) {
			LOG.trace("getEventDetectorTemplatesWithoutDetectors()");
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<EventDetectorTemplateVO> list= DAO.getInstance().getJdbcTemp().query(TEMPLATES_SELECT, new RowMapper() {
					@Override
					public EventDetectorTemplateVO mapRow(ResultSet rs, int rownumber) throws SQLException {
						
							EventDetectorTemplateVO eventDetectorTempl = new EventDetectorTemplateVO();
							eventDetectorTempl.setId(rs.getInt(COLUMN_NAME_EVENT_DETEC_TEMPL_ID));
							eventDetectorTempl.setName(rs.getString(COLUMN_NAME_EVENT_DETEC_TEMPL_NAME));
							return eventDetectorTempl;
						
					}
				});
		return list;
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW,isolation=Isolation.READ_COMMITTED,rollbackFor=SQLException.class)
	public void insertEventDetectorTemplate(final EventDetectorTemplateVO eventDetectorTemplate) {
		
		if (LOG.isTraceEnabled()) {
			LOG.trace(eventDetectorTemplate);
		}
		
		String templateSelectWhereName = TEMPLATES_SELECT +" where " + COLUMN_NAME_EVENT_DETEC_TEMPL_NAME + "=? ";
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<EventDetectorTemplateVO> listEventDetectorTemplate = DAO.getInstance().getJdbcTemp().query(templateSelectWhereName, new String[]{eventDetectorTemplate.getName()}, new RowMapper() {
					@Override
					public EventDetectorTemplateVO mapRow(ResultSet rs, int rownumber) throws SQLException {
						
							EventDetectorTemplateVO eventDetectorTempl = new EventDetectorTemplateVO();
							eventDetectorTempl.setId(rs.getInt(COLUMN_NAME_EVENT_DETEC_TEMPL_ID));
							eventDetectorTempl.setName(rs.getString(COLUMN_NAME_EVENT_DETEC_TEMPL_NAME));
							return eventDetectorTempl;
						
					}
		});
		
		if (listEventDetectorTemplate.size() > 0) {
			throw new EventDetectorTemplateExceptionDAO();
		}
		
		DAO.getInstance().getJdbcTemp().update(TEMPLATES_INSERT, new Object[]{eventDetectorTemplate.getName()});
		
		for (PointEventDetectorVO ped : eventDetectorTemplate.getEventDetectors()) {
			DAO.getInstance().getJdbcTemp().update(DETECTORS_INSERT, new Object[]{
					ped.getXid(), 
					ped.getAlias(),
					ped.getDetectorType(), 
					ped.getAlarmLevel(),
					ped.getLimit(), 
					ped.getDuration(),
					ped.getDurationType(),
					DAO.boolToChar(ped.isBinaryState()),
					ped.getMultistateState(), ped.getChangeCount(),
					ped.getAlphanumericState(), ped.getWeight()});
		}
	}
	
}