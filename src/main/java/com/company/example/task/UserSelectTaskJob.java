package com.company.example.task;

import com.af.taskhandle.TaskHelperBo;
import com.company.example.dao.UserDAO;
import com.company.example.model.User;
import com.jtool.support.log.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Repository
public class UserSelectTaskJob {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Resource
	private UserDAO userDAO;

	/*
	<logicInfo>
		1:每1分钟触发一次，查询数据库记录<br/>
	</logicInfo>
	 */
	@Scheduled(cron="0 */1 * * * ?")
	public void run() {
		//定时器没有经过Filter,所以手动加上_logId方便查询日志
		LogHelper.setLogId(UUID.randomUUID().toString());
		LogHelper.setProjectName("example");

		log.debug("执行UserSelectTaskJob定时器");
		boolean isRun = TaskHelperBo.isRun(redisTemplate, "taskRechargePalmCoinFail", 10 * 60);
		if(!isRun) {
			return;
		}

		List<User> users = userDAO.getUserList();
		log.debug("UserSelectTaskJob定时器查询数据：" + users);

	}

}
