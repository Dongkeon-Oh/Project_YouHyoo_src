package youhyoo;

public class InsertDto {
	
	//pension 13
	private int p_num;
	private String p_name;
	private String p_addr1;
	private String p_addr2;
	private String p_addr3;
	private String p_tel;
	private int p_paytype;
	private String p_account;
	private String p_intro;
	private String p_photo;//!!!!!!!!!!!!!!!!!!!!!
	private String p_contect;
	private double p_lat;
	private double p_lng;
	
	//room 11
	private int r_num;
	private String r_name;
	private int r_max_wd;
	private int r_max_we;
	private int r_min_wd;
	private int r_min_we;
	private int r_maxcapa;
	private int r_mincapa;
	private int r_size;
	private String r_photo;//!!!!!!!!!!!!!!!!!!!!!
	private int r_pension;
	
	//Roomdetail_Around 6
	private int ra_pnum;
	private boolean ra_sea;///*************true/false
	private boolean ra_valley;///*************true/false
	private boolean ra_river;///*************true/false
	private boolean ra_mountain;///*************true/false
	private boolean ra_island;///*************true/false
	
	//Roomdetail_Support 11
	private int rs_pnum; 
	private String rs_market;
	private String rs_meal;
	private String rs_pet;
	private String rs_party; 
	private String rs_board;
	private String rs_pickup;
	private String rs_inet;
	private String rs_movie;
	private String rs_cafe;
	private String rs_shuttle;
	
	//Roomdetail_Facility 13
	private int rf_pnum;
	private String rf_pool;
	private String rf_slide;
	private String rf_soccer;
	private String rf_jokgoo;
	private String rf_bbq;
	private String rf_campfire;
	private String rf_karaoke;
	private String rf_basketball;
	private String rf_seminar;
	private String rf_bike;
	private String rf_4wbike;
	private String rf_servival;
	
	//Roomdetail_Structure 5
	private int rr_pnum;
	private String rr_2floor;
	private String rr_single;
	private String rr_spa;
	private String rr_terrace;
	
	//getter,setter
	public int getP_num() {
		return p_num;
	}
	public void setP_num(int p_num) {
		this.p_num = p_num;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_addr1() {
		return p_addr1;
	}
	public void setP_addr1(String p_addr1) {
		this.p_addr1 = p_addr1;
	}
	public String getP_addr2() {
		return p_addr2;
	}
	public void setP_addr2(String p_addr2) {
		this.p_addr2 = p_addr2;
	}
	public String getP_addr3() {
		return p_addr3;
	}
	public void setP_addr3(String p_addr3) {
		this.p_addr3 = p_addr3;
	}
	public String getP_tel() {
		return p_tel;
	}
	public void setP_tel(String p_tel) {
		this.p_tel = p_tel;
	}
	public int getP_paytype() {
		return p_paytype;
	}
	public void setP_paytype(int p_paytype) {
		this.p_paytype = p_paytype;
	}
	public String getP_account() {
		return p_account;
	}
	public void setP_account(String p_account) {
		this.p_account = p_account;
	}
	public String getP_intro() {
		return p_intro;
	}
	public void setP_intro(String p_intro) {
		this.p_intro = p_intro;
	}
	public String getP_photo() {
		return p_photo;
	}
	public void setP_photo(String p_photo) {
		this.p_photo = p_photo;
	}
	public String getP_contect() {
		return p_contect;
	}
	public void setP_contect(String p_contect) {
		this.p_contect = p_contect;
	}
	public double getP_lat() {
		return p_lat;
	}
	public void setP_lat(double p_lat) {
		this.p_lat = p_lat;
	}
	public double getP_lng() {
		return p_lng;
	}
	public void setP_lng(double p_lng) {
		this.p_lng = p_lng;
	}
	public int getR_num() {
		return r_num;
	}
	public void setR_num(int r_num) {
		this.r_num = r_num;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public int getR_max_wd() {
		return r_max_wd;
	}
	public void setR_max_wd(int r_max_wd) {
		this.r_max_wd = r_max_wd;
	}
	public int getR_max_we() {
		return r_max_we;
	}
	public void setR_max_we(int r_max_we) {
		this.r_max_we = r_max_we;
	}
	public int getR_min_wd() {
		return r_min_wd;
	}
	public void setR_min_wd(int r_min_wd) {
		this.r_min_wd = r_min_wd;
	}
	public int getR_min_we() {
		return r_min_we;
	}
	public void setR_min_we(int r_min_we) {
		this.r_min_we = r_min_we;
	}
	public int getR_maxcapa() {
		return r_maxcapa;
	}
	public void setR_maxcapa(int r_maxcapa) {
		this.r_maxcapa = r_maxcapa;
	}
	public int getR_mincapa() {
		return r_mincapa;
	}
	public void setR_mincapa(int r_mincapa) {
		this.r_mincapa = r_mincapa;
	}
	public int getR_size() {
		return r_size;
	}
	public void setR_size(int r_size) {
		this.r_size = r_size;
	}
	public String getR_photo() {
		return r_photo;
	}
	public void setR_photo(String r_photo) {
		this.r_photo = r_photo;
	}
	public int getR_pension() {
		return r_pension;
	}
	public void setR_pension(int r_pension) {
		this.r_pension = r_pension;
	}
	public int getRa_pnum() {
		return ra_pnum;
	}
	public void setRa_pnum(int ra_pnum) {
		this.ra_pnum = ra_pnum;
	}
	public boolean isRa_sea() {
		return ra_sea;
	}
	public void setRa_sea(boolean ra_sea) {
		this.ra_sea = ra_sea;
	}
	public boolean isRa_valley() {
		return ra_valley;
	}
	public void setRa_valley(boolean ra_valley) {
		this.ra_valley = ra_valley;
	}
	public boolean isRa_river() {
		return ra_river;
	}
	public void setRa_river(boolean ra_river) {
		this.ra_river = ra_river;
	}
	public boolean isRa_mountain() {
		return ra_mountain;
	}
	public void setRa_mountain(boolean ra_mountain) {
		this.ra_mountain = ra_mountain;
	}
	public boolean isRa_island() {
		return ra_island;
	}
	public void setRa_island(boolean ra_island) {
		this.ra_island = ra_island;
	}
	public int getRs_pnum() {
		return rs_pnum;
	}
	public void setRs_pnum(int rs_pnum) {
		this.rs_pnum = rs_pnum;
	}
	public String getRs_market() {
		return rs_market;
	}
	public void setRs_market(String rs_market) {
		this.rs_market = rs_market;
	}
	public String getRs_meal() {
		return rs_meal;
	}
	public void setRs_meal(String rs_meal) {
		this.rs_meal = rs_meal;
	}
	public String getRs_pet() {
		return rs_pet;
	}
	public void setRs_pet(String rs_pet) {
		this.rs_pet = rs_pet;
	}
	public String getRs_party() {
		return rs_party;
	}
	public void setRs_party(String rs_party) {
		this.rs_party = rs_party;
	}
	public String getRs_board() {
		return rs_board;
	}
	public void setRs_board(String rs_board) {
		this.rs_board = rs_board;
	}
	public String getRs_pickup() {
		return rs_pickup;
	}
	public void setRs_pickup(String rs_pickup) {
		this.rs_pickup = rs_pickup;
	}
	public String getRs_inet() {
		return rs_inet;
	}
	public void setRs_inet(String rs_inet) {
		this.rs_inet = rs_inet;
	}
	public String getRs_movie() {
		return rs_movie;
	}
	public void setRs_movie(String rs_movie) {
		this.rs_movie = rs_movie;
	}
	public String getRs_cafe() {
		return rs_cafe;
	}
	public void setRs_cafe(String rs_cafe) {
		this.rs_cafe = rs_cafe;
	}
	public String getRs_shuttle() {
		return rs_shuttle;
	}
	public void setRs_shuttle(String rs_shuttle) {
		this.rs_shuttle = rs_shuttle;
	}
	public int getRf_pnum() {
		return rf_pnum;
	}
	public void setRf_pnum(int rf_pnum) {
		this.rf_pnum = rf_pnum;
	}
	public String getRf_pool() {
		return rf_pool;
	}
	public void setRf_pool(String rf_pool) {
		this.rf_pool = rf_pool;
	}
	public String getRf_slide() {
		return rf_slide;
	}
	public void setRf_slide(String rf_slide) {
		this.rf_slide = rf_slide;
	}
	public String getRf_soccer() {
		return rf_soccer;
	}
	public void setRf_soccer(String rf_soccer) {
		this.rf_soccer = rf_soccer;
	}
	public String getRf_jokgoo() {
		return rf_jokgoo;
	}
	public void setRf_jokgoo(String rf_jokgoo) {
		this.rf_jokgoo = rf_jokgoo;
	}
	public String getRf_bbq() {
		return rf_bbq;
	}
	public void setRf_bbq(String rf_bbq) {
		this.rf_bbq = rf_bbq;
	}
	public String getRf_campfire() {
		return rf_campfire;
	}
	public void setRf_campfire(String rf_campfire) {
		this.rf_campfire = rf_campfire;
	}
	public String getRf_karaoke() {
		return rf_karaoke;
	}
	public void setRf_karaoke(String rf_karaoke) {
		this.rf_karaoke = rf_karaoke;
	}
	public String getRf_basketball() {
		return rf_basketball;
	}
	public void setRf_basketball(String rf_basketball) {
		this.rf_basketball = rf_basketball;
	}
	public String getRf_seminar() {
		return rf_seminar;
	}
	public void setRf_seminar(String rf_seminar) {
		this.rf_seminar = rf_seminar;
	}
	public String getRf_bike() {
		return rf_bike;
	}
	public void setRf_bike(String rf_bike) {
		this.rf_bike = rf_bike;
	}
	public String getRf_4wbike() {
		return rf_4wbike;
	}
	public void setRf_4wbike(String rf_4wbike) {
		this.rf_4wbike = rf_4wbike;
	}
	public String getRf_servival() {
		return rf_servival;
	}
	public void setRf_servival(String rf_servival) {
		this.rf_servival = rf_servival;
	}
	public int getRr_pnum() {
		return rr_pnum;
	}
	public void setRr_pnum(int rr_pnum) {
		this.rr_pnum = rr_pnum;
	}
	public String getRr_2floor() {
		return rr_2floor;
	}
	public void setRr_2floor(String rr_2floor) {
		this.rr_2floor = rr_2floor;
	}
	public String getRr_single() {
		return rr_single;
	}
	public void setRr_single(String rr_single) {
		this.rr_single = rr_single;
	}
	public String getRr_spa() {
		return rr_spa;
	}
	public void setRr_spa(String rr_spa) {
		this.rr_spa = rr_spa;
	}
	public String getRr_terrace() {
		return rr_terrace;
	}
	public void setRr_terrace(String rr_terrace) {
		this.rr_terrace = rr_terrace;
	}
	
}
