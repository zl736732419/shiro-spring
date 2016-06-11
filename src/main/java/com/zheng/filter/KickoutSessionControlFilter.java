package com.zheng.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.zheng.utils.Constants;


/**
 * 并发登录控制统一帐号登录次数
 * 
 * @author Administrator
 *
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

	private String kickoutUrl = "";// 踢出后跳转到地址
	private boolean kickoutAfter = false; // 踢出之前的用户
	private int maxSession = 1; // 同一帐号允许登录的人次

	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;

	public String getKickoutUrl() {
		return kickoutUrl;
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public boolean isKickoutAfter() {
		return kickoutAfter;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public int getMaxSession() {
		return maxSession;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public Cache<String, Deque<Serializable>> getCache() {
		return cache;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("shiro-kickout-session");
	}
	

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 判断是否登录，没有登录直接进行后续流程
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			return true;
		}

		Session session = subject.getSession();
		String sid = (String) session.getId();
		String username = (String) subject.getPrincipal();
		
		Deque<Serializable> deque = cache.get(username);
		if(deque == null) {
			deque = new LinkedList<>();
			cache.put(username, deque);
		}
		
		//如果队列中不存在该用户sessionid, 并且当前用户不是踢出用户
		if(!deque.contains(sid) && session.getAttribute(Constants.KICKOUT_SESSION) == null) {
			deque.push(sid);
		}
		
		//超过最大访问次数开始踢人
		String sessionId = null;
		while(deque.size() > maxSession) {
			if(kickoutAfter) {
				sessionId = (String)deque.removeLast();
			}else {
				sessionId = (String) deque.removeFirst();
			}
			
			Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
			if(kickoutSession != null) {
				kickoutSession.setAttribute(Constants.KICKOUT_SESSION, true);
			}
		}
		
		//当前登录用户已经被踢出
		if(session.getAttribute(Constants.KICKOUT_SESSION) != null) {
			subject.logout();
			//退出后跳转到指定的页面
			saveRequest(request);
			WebUtils.issueRedirect(request, response, kickoutUrl);
			return false;
		}
		
		return true;
	}

}
