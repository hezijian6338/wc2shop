package com.bootdo.wap;


import com.bootdo.common.utils.MD5Utils;
import com.bootdo.shop.domain.TGoodsDO;
import com.bootdo.shop.domain.TMemberDO;
import com.bootdo.shop.service.TArticleService;
import com.bootdo.shop.service.TBrandService;
import com.bootdo.shop.service.TCartService;
import com.bootdo.shop.service.TGoodsClassService;
import com.bootdo.shop.service.TGoodsService;
import com.bootdo.shop.service.TGoodsTypeService;
import com.bootdo.shop.service.TMemberService;
import com.bootdo.shop.service.TOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
	 * 
	 * @author zsCat 2016-10-31 14:01:30
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	商品管理
	 */
@Controller
@RequestMapping("/wap")
public class Wap1IndexController {
	@Autowired
	private TArticleService tArticleService;
	@Autowired
	private TBrandService tBrandService;
	@Autowired
	private TGoodsTypeService tGoodsTypeService;
	@Autowired
	private TGoodsService tGoodsService;
	@Autowired
	private TGoodsClassService tGoodsClassService;
	@Autowired
	private TMemberService tMemberService;
	@Autowired
	private TCartService tCartService;
	@Autowired
	private TOrderService orderService;
	 @RequestMapping("")
	  public ModelAndView index(HttpServletRequest req) {
	        try {
	            ModelAndView model = new ModelAndView("/wap/index");
				Map<String, Object> params = new HashMap<>();
				params.put("sort","clickHit");
				params.put("order","desc");
				model.addObject("hitList", tGoodsService.list(params));
				params.clear();
				params.put("sort","create_date");
				params.put("order","desc");
				model.addObject("xinpinList", tGoodsService.list(params));
				params.clear();
				params.put("iscom","1");
				model.addObject("commList", tGoodsService.list(params));
				if (MemberUtils.getSessionLoginUser()!=null){
					model.addObject("city", MemberUtils.getSessionLoginUser().getUsername());
				}else{
					model.addObject("city", "未登录");
				}
	            return model;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("导航失败!");
	        }
	    }
	 @RequestMapping("/goodsDetail/{id}")
		public ModelAndView goodsDetail(@PathVariable("id") Long id, HttpServletRequest req)throws Exception{
			ModelAndView mav=new ModelAndView();
			TGoodsDO goods=tGoodsService.get(id);
			mav.addObject("goods", goods);
			if(goods.getImgmore()!=null && goods.getImgmore().indexOf(",")>-1){
				mav.addObject("imgs", goods.getImgmore().split(","));
			}
			mav.setViewName("wap/goodsDetail");
			goods.setClickhit(goods.getClickhit()+1);
			tGoodsService.update(goods);
			//查询详情商品的 其他商品

		 Map<String, Object> params = new HashMap<>();
		 params.put("createBy",goods.getCreateBy());

			mav.addObject("ownGoods", tGoodsService.list(params));

			return mav;
		}

	 @RequestMapping("/information/{createBy}")
	  public ModelAndView information(@PathVariable("createBy") Long createBy) {
		 ModelAndView model = new ModelAndView("/wap/person/information");
        TMemberDO member=tMemberService.get(createBy);
        model.addObject("member", member);
		 return model;
	 }
	 /**
	  * 商城公告
	  * @param
	  * @return
	  */
	 @RequestMapping("/newD/{id}")
	  public ModelAndView newD(@PathVariable("id") Long id) {
		 ModelAndView model = new ModelAndView("/wap/person/blog");
//        Article article=articleService.selectByPrimaryKey(id);
//        model.addObject("article", article);
//        List<Article> articleList=articleService.select(new Article());
//        model.addObject("articleList", articleList);
		 return model;
	 }
	   /**
		 * 跳转到登录页面
		 * 
		 * @return
		 */

	@RequestMapping(value = "login")
	public ModelAndView toLogin1() {
		ModelAndView model = new ModelAndView("/wap/login");
		if( MemberUtils.getSessionLoginUser() != null){
			return new ModelAndView("redirect:/wap");
		}
		return model;
	}

		/**
		 * 登录验证
		 * 
		 * @param username
		 * @param password
		 * @param
		 * @return
		 */
		@RequestMapping(value = "login", method = RequestMethod.POST)
		public @ResponseBody
        Map<String, Object> checkLogin(String username,
                                       String password, HttpServletRequest request) {

			Map<String, Object> msg = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			//code = StringUtils.trim(code);
			username = StringUtils.trim(username);
			password = StringUtils.trim(password);

			TMemberDO user = tMemberService.checkUser(username, password);
			if (null != user) {
				Map<String, Object> params = new HashMap<>();
				session.setAttribute(MemberUtils.SESSION_LOGIN_MEMBER, tMemberService.selectOne(params));
			} else {
				msg.put("error", "用户名或密码错误");
			}
			return msg;
		}
	 
		 /**
		 * 跳转到登录页面
		 * 
		 * @return
		 */
		@RequestMapping(value = "reg", method = RequestMethod.GET)
		public String reg() {
			if( MemberUtils.getSessionLoginUser() != null){
				return "redirect:/wap";
			}
			return "/wap/register";
		}
	
		@RequestMapping(value = "reg", method = RequestMethod.POST)
		public @ResponseBody
        Map<String, Object> reg(
                @RequestParam(value = "password",required=true)String  password,
                @RequestParam(value = "email",required=false)String email,
                @RequestParam(value = "phone",required=false)String phone,
                @RequestParam(value = "username")String username,
                @RequestParam(value = "passwordRepeat",required=true)String passwordRepeat, HttpServletRequest request) {
			Map<String, Object> msg = new HashMap<String, Object>();
			if (!StringUtils.equalsIgnoreCase(password, passwordRepeat)) {
				msg.put("error", "密码不一致!");
				return msg;
			}
			String secPwd = null ;
			TMemberDO m=new TMemberDO();
			secPwd = MD5Utils.encrypt(password, username);
			m.setUsername(username);
			m.setPassword(secPwd);
			m.setTruename(m.getUsername());
			m.setPhone(phone);
			try {
				HttpSession session = request.getSession();
				int result = tMemberService.save(m);
				System.out.println(m.getId());
				if (result == 1) {
					Map<String, Object> params = new HashMap<>();

					session.setAttribute(MemberUtils.SESSION_LOGIN_MEMBER, tMemberService.get(m.getId()));
				} else {
					msg.put("error", "注册失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return msg;
		}
	 	/**
		 * 用户退出
		 * 
		 * @return 跳转到登录页面
		 */
		@RequestMapping("logout")
		public String logout(HttpServletRequest request) {
			request.getSession().invalidate();
			return "redirect:/wap/login";
		}
	

}
