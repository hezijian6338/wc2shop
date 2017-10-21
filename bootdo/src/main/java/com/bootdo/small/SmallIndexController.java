package com.bootdo.small;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.bootdo.common.utils.JSONUtils;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.shop.controller.TBrandController;
import com.bootdo.shop.domain.AddressDO;
import com.bootdo.shop.domain.ArticleDO;
import com.bootdo.shop.domain.BannerDO;
import com.bootdo.shop.domain.CouponDO;
import com.bootdo.shop.domain.FavoriteDO;
import com.bootdo.shop.domain.TArticleDO;
import com.bootdo.shop.domain.TBrandDO;
import com.bootdo.shop.domain.TCartDO;
import com.bootdo.shop.domain.TGoodsDO;
import com.bootdo.shop.domain.TGoodsTypeDO;
import com.bootdo.shop.domain.TMemberDO;
import com.bootdo.shop.domain.TReplyDO;
import com.bootdo.shop.domain.TStoreDO;
import com.bootdo.shop.domain.TopicDO;
import com.bootdo.shop.service.*;
import com.bootdo.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-10-15 15:07:36
 */
 
@Controller
@RequestMapping("/small")
public class SmallIndexController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private TBrandService tBrandService;
	@Autowired
	private TGoodsTypeService tGoodsTypeService;
	@Autowired
	private TGoodsService tGoodsService;
	@Autowired
	private TGoodsClassService tGoodsClassService;
	@Autowired
	private TStoreService tStoreService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private TCartService tCartService;
	@Autowired
	private TReplyService tReplyService;
	@Autowired
	private TMemberService tMemberService;
	@Autowired
	private WxMaService wxService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private FavoriteService favoriteService;
	/**
	 * banner图列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/banner/list")
	public R banner(@RequestParam Map<String, Object> params) {
		params.put("offset", 0);
		//查询列表数据
		R r = new R();
		try {
			Query query = new Query(params);
			List<TBrandDO> tArticleList = tBrandService.list(query);
			int total = tBrandService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);

			r.put("data", pageUtils);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 品牌列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/brand/list")
	public R brand(@RequestParam Map<String, Object> params) {
		params.put("offset", 0);
		//查询列表数据
		R r = new R();
		try {
			Query query = new Query(params);
			List<TBrandDO> tArticleList = tBrandService.list(query);
			int total = tBrandService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data", pageUtils);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		return r;
	}

	/**
	 * 品牌下的商品
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/brand/index")
	public R brandGoods(@RequestParam Map<String, Object> params,HttpServletRequest req) {
		params.put("offset", 0);
		params.put("limit", 12);
		//查询列表数据
		R r = new R();
		try {
			Query query = new Query(params);
			List<TGoodsDO> tArticleList = tGoodsService.list(query);
			int total = tGoodsService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data", pageUtils);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 所有的分类
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/category/all")
	public R category(@RequestParam Map<String, Object> params) {
		params.put("offset", 0);
		params.put("limit", 10);
		//查询列表数据
		R r = new R();
		try {
			Query query = new Query(params);
			List<TGoodsTypeDO> tArticleList = tGoodsTypeService.list(query);
			int total = tGoodsTypeService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);

			r.put("data", pageUtils);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 商品列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/goods/list")
	public R goods(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("sort","clickHit");
		params.put("order","desc");
		R r=new R();
		try {
			Query query = new Query(params);
			List<TGoodsDO> tArticleList = tGoodsService.list(query);
			int total = tGoodsService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}

	/**
	 * 商品详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/details")
	public R goodsDetail(HttpServletRequest req)throws Exception{
		Long id = Long.parseLong(req.getParameter("id"));

		R r=new R();
		try {
			TGoodsDO goods=tGoodsService.get(id);
			r.put("data",goods);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 文章列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/article/list")
	public R article(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<ArticleDO> tArticleList = articleService.list(query);
			int total = articleService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("page",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	@PostMapping( "/user/check-token")
	@ResponseBody
	public R remove( String  token){
//		if(tArticleService.remove(id)>0){
//			return R.ok();
//		}
		return R.error();
	}


	/**
	 * 文章列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/default/index")
	public R index(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		List<TArticleDO> model_list = new ArrayList<>();
		List<TArticleDO> nav_icon_list = new ArrayList<>();
		IndexData data = new IndexData();
		R r=new R();
		try {
			TArticleDO a = new TArticleDO("banner");
			TArticleDO a1 = new TArticleDO("search");
			TArticleDO a2 = new TArticleDO("nav");
			TArticleDO a3 = new TArticleDO("cat");
			TArticleDO a4 = new TArticleDO("coupon");
			TArticleDO a5 = new TArticleDO("topic");
			TArticleDO b2 = new TArticleDO("block","3");
			TArticleDO b1 = new TArticleDO("block","4");
			TArticleDO b3 = new TArticleDO("block","5");
			model_list.add(a);model_list.add(a1);model_list.add(a2);model_list.add(a3);
			model_list.add(a4);model_list.add(a5);
			model_list.add(b1);model_list.add(b2);model_list.add(b3);

			TStoreDO store = tStoreService.get(1L);

			params.put("limit", 3);
			List<BannerDO> bannerList = bannerService.list(params);

			params.put("limit", 3);
			List<CouponDO> couponList = couponService.list(params);

			List<TopicDO> tArticleList = topicService.list(params);

			TArticleDO c1 = new TArticleDO("全部商品","/pages/list/list?cat_id=","navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/86/863a7db352a936743faf8edd5162bb5c.png");
			TArticleDO c2 = new TArticleDO("商品分类","/pages/cat/cat","switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/35/3570994c06e61b1f0cf719bdb52a0053.png");
			TArticleDO c3 = new TArticleDO("购物车","/pages/cart/cart","switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/c2/c2b01cf78f79cbfba192d5896eeaecbe.png");
			TArticleDO c4 = new TArticleDO("我的订单","/pages/order/order?status=-1","navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/7c/7c80acbbd479b099566cc6c3d34fbcb8.png");
			TArticleDO c5 = new TArticleDO("用户中心","/pages/user/user","switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/46/46eabbff1e7dc5e416567fc45d4d5df3.png");
			TArticleDO c6 = new TArticleDO("优惠劵","/pages/coupon/coupon","navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/13/13312a6d56c202330f8c282d8cf84ada.png");
			TArticleDO c7 = new TArticleDO("我的收藏","/pages/favorite/favorite","navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/ca/cab6d8d4785e43bd46dcbb52ddf66f61.png");
			TArticleDO c8 = new TArticleDO("售后订单","/pages/order/order?status=4","navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/cf/cfb32a65d845b4e9a9778020ed2ccac6.png");
			nav_icon_list.add(c1);nav_icon_list.add(c2);nav_icon_list.add(c3);
			nav_icon_list.add(c4);nav_icon_list.add(c5);nav_icon_list.add(c6);
			nav_icon_list.add(c7);nav_icon_list.add(c8);

			List<TGoodsTypeDO> goodsTypeDOList = tGoodsTypeService.list(params);
			for (TGoodsTypeDO g :goodsTypeDOList){
				params.clear();
				params.put("typeid",g.getId());
				List<TGoodsDO> goodsDOList = tGoodsService.list(params);
				g.setGoods_list(goodsDOList);
			}

			data.setCat_goods_cols(2);
			data.setCat_list(goodsTypeDOList);
			data.setTopic_list(tArticleList);data.setNav_icon_list(nav_icon_list);
			data.setBanner_list(bannerList);data.setCoupon_list(couponList);
			data.setModule_list(model_list);data.setStore(store);
			r.put("data",data);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 商品列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/default/goods-list")
	public R goodsList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		//params.put("limit", 10);
		//params.put("sort","clickHit");
	//	params.put("order","desc");
		R r=new R();
		try {
			Query query = new Query(params);
			List<TGoodsDO> tArticleList = tGoodsService.list(query);
			int total = tGoodsService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 商品详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/default/goods")
	public R defaultgoodsDetail(HttpServletRequest req)throws Exception{
		Long id = Long.parseLong(req.getParameter("id"));
		R r=new R();
		try {
			TGoodsDO goods=tGoodsService.get(id);
			String imgs[] =new String[4];
			if(goods.getImgmore()!=null && goods.getImgmore().indexOf(",")>-1){
				imgs = goods.getImgmore().split(",");
				imgs[0] = "https://jdweixin.tunnel.qydev.com/upload/project/"+goods.getImg();
			}else {
				imgs[0] = "https://jdweixin.tunnel.qydev.com/upload/project/"+goods.getImg();
			}
			goods.setImgs(imgs);
			goods.setClickhit(goods.getClickhit()+1);
			tGoodsService.update(goods);

			r.put("data",goods);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}

	/**
	 * 店铺详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/default/store")
	public R storeDetail(HttpServletRequest req)throws Exception{
		Long id = Long.parseLong(req.getParameter("id"));
		R r=new R();
		try {
			TStoreDO goods=tStoreService.get(id);
			r.put("data",goods);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 购物车列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/cart/cart-list")
	public R cartList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<TCartDO> tArticleList = tCartService.list(query);
			int total = tCartService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 分类列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/default/cat-list")
	public R catList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<TGoodsTypeDO> tGoodsTypeDOS = tGoodsTypeService.list(query);
			int total = tGoodsTypeService.count(query);
			for (TGoodsTypeDO gt : tGoodsTypeDOS){
				params.clear();
				params.put("typeid",gt.getId());
				List<TGoodsDO> goodsDOList = tGoodsService.list(params);
				gt.setGoods_list(goodsDOList);
			}
			PageUtils pageUtils = new PageUtils(tGoodsTypeDOS, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 商品评论列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/default/comment-list")
	public R commentList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<TReplyDO> tArticleList = tReplyService.list(query);
			int total = tReplyService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 用户地址列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/user/address-list")
	public R addressList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<AddressDO> tArticleList = addressService.list(query);
			int total = addressService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 地址详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/address-detail")
	public R addressDetail(HttpServletRequest req)throws Exception{
		Long id = Long.parseLong(req.getParameter("id"));
		R r=new R();
		try {
			AddressDO goods=addressService.get(id);
			r.put("data",goods);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 用户详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/index")
	public R memberDetail(HttpServletRequest req)throws Exception{
		Long id = Long.parseLong(req.getParameter("id"));
		R r=new R();
		try {
			TMemberDO goods=tMemberService.get(id);
			r.put("data",goods);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
//	address_save: _api_root + 'user/address-save',
	/**
	 * 添加地址
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/address-save")
	public R addressSave(HttpServletRequest req)throws Exception{

		R r=new R();
		try {
			AddressDO addressDO =new AddressDO();

			addressService.save(addressDO);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
//	address_set_default: _api_root + 'user/address-set-default',
	/**
	 * 修改地址
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/address-update")
	public R addressUpdate(HttpServletRequest req)throws Exception{

		R r=new R();
		try {
			AddressDO addressDO =new AddressDO();

			addressService.update(addressDO);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
//	address_delete: _api_root + 'user/address-delete',
	/**
	 * 删除
	 */
	@PostMapping( "/user/address-delete")
	@ResponseBody
	public R remove( Long id){
		if(addressService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}
//	save_form_id: _api_root + "user/save-form-id",
	/**
	 * 添加地址
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/save-form-id")
	public R saveformid(HttpServletRequest req)throws Exception{

		R r=new R();
		try {
			AddressDO addressDO =new AddressDO();

			addressService.save(addressDO);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
//	favorite_add: _api_root + "user/favorite-add",
	/**
	 * 添加地址
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/favorite-add")
	public R favoriteadd(@RequestParam Map<String, Object> params,HttpServletRequest req)throws Exception{
		Long userid=Long.parseLong(req.getParameter("userid"));
		Long goodsid=Long.parseLong(req.getParameter("goodsid"));
		params.put("offset", 0);
		R r=new R();
		try {
			FavoriteDO favoriteDO = favoriteService.selectOne(params);
			if (favoriteDO!=null){
				//更新状态
				favoriteDO.setDeletestatus(true);
				favoriteDO.setAddtime(new Date());
				favoriteService.update(favoriteDO);
			}else{
				favoriteDO.setType(1); //增加
				favoriteDO.setDeletestatus(false);
				favoriteDO.setAddtime(new Date());
				favoriteService.save(favoriteDO);
			}
			r.put("data",favoriteDO);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
//	favorite_remove: _api_root + "user/favorite-remove",
	/**
	 * 删除
	 */
	@PostMapping( "/user/favorite-remove")
	@ResponseBody
	public R favoriteremove( Long id){
		if(favoriteService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}
//	favorite_list: _api_root + "user/favorite-list",
	/**
	 * 话题列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/user/favorite-list")
	public R goodsfavoriteList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<FavoriteDO> tArticleList = favoriteService.userFavorite(query);
			r.put("data",tArticleList);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 话题列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/user/topic-favorite-list")
	public R topicfavoriteList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<FavoriteDO> tArticleList = favoriteService.userTopicFavorite(query);
			r.put("data",tArticleList);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
//	index: _api_root + "user/index",
//	wechat_district: _api_root + "user/wechat-district",
//	add_wechat_address: _api_root + "user/add-wechat-address",
//	topic_favorite: _api_root + "user/topic-favorite",
//	topic_favorite_list: _api_root + "user/topic-favorite-list",
	/**
	 * 话题列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/default/topic-list")
	public R topicList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<TopicDO> tArticleList = topicService.list(query);
			int total = topicService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 店铺详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/default/topic")
	public R topicDetail(HttpServletRequest req)throws Exception{
		Long id = Long.parseLong(req.getParameter("id"));
		R r=new R();
		try {
			TopicDO goods=topicService.get(id);
			r.put("data",goods);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 优惠劵列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/default/coupon-list")
	public R couponList(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<CouponDO> tArticleList = couponService.list(query);
			int total = couponService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}
	/**
	 * 我领取优惠劵列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/coupon/index")
	public R userCoupon(@RequestParam Map<String, Object> params){
		params.put("offset", 0);
		R r=new R();
		try {
			Query query = new Query(params);
			List<CouponDO> tArticleList = couponService.userCoupon(query);
			int total = couponService.count(query);
			PageUtils pageUtils = new PageUtils(tArticleList, total);
			r.put("data",pageUtils);
		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
		return r;
	}

	/**
	 * 店铺详情
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/password/login")
	public R login(HttpServletRequest req)throws Exception{
		R r=new R();
		String code = req.getParameter("code");
		if (StringUtils.isBlank(code)) {
			System.out.println("code is empty");
		}
			WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
		Map<String, Object> params = new HashMap<>();
		params.put("password",session.getOpenid());
		TMemberDO seMember = tMemberService.selectOne(params);
		if (seMember == null){
//			System.out.println(session.getSessionKey());
//			System.out.println(session.getOpenid());
//			System.out.println(session.getExpiresin().toString());
//			System.out.println(JsonUtils.toJson(session));
			String userInfo = req.getParameter("userInfo");
			System.out.println(userInfo);
			Map<String,Object> me = JSONUtils.jsonToMap(userInfo);
			TMemberDO m =new TMemberDO();
			m.setImg(me.get("avatarUrl").toString());
			if ("1".equals(me.get("gender").toString())){
				m.setSex("男");
			}else{
				m.setSex("女");
			}
			m.setPassword(session.getOpenid());
			m.setAddress(me.get("country").toString()+"-"+me.get("province").toString()+"-"+me.get("city").toString());
			m.setAddtime(new Date());
			m.setGold(0);
			m.setStatus(0);
			m.setStoreid(1L);
			m.setTruename(me.get("nickName").toString());
			m.setUsername(me.get("nickName").toString());

			try {
				int i =tMemberService.save(m);
				r.put("data",tMemberService.get(m.getId()));
			}catch (Exception e){
				e.printStackTrace();
				return R.error();
			}
		}else{
			r.put("data",seMember);
		}

		return r;
	}
}
