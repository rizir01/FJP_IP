/**
 * Define a grammar called Hello
 */
grammar IPv4;

program : blok;

blok : deklarace_promennych funkce* hlavni_funkce;

//TODO
deklarace_promennych : dekl_konstanta* dekl_promenna*;

dekl_konstanta: 	konstanta 	ident_prirazeni hodnota 'sz_com';

dekl_promenna: 		promenna 	ident_prirazeni hodnota 'sz_com';

konstanta 	:  	k_int | k_char | k_bl;

promenna	: 	p_int | p_char | p_bl;

k_int : 'i_intc:' NUM;
k_char : 'i_charc:' NUM;
k_bl : 'i_blc:' NUM;

p_int : 'i_int:' NUM;
p_char : 'i_char:' NUM;
p_bl : 'i_bl:' NUM;


hodnota : 'v_byte:' NUM;

hlavni_funkce : 'f_int:0' kod;
funkce: 'f_int:' NUM kod | 'f_char:' NUM kod | 'f_bl:' NUM kod; 


//TODO
kod : 'sz_{' telo  (ret 'sz_com')? 'sz_}';

telo: prikaz*;

prikaz: volani_fce 'sz_com' | prirazovani 'sz_com' | podminky | cykly;

volani_fce : 'f_int:' NUM | 'f_char:' NUM | 'f_bl:' NUM;
prirazovani : norm_prirazeni | tern_prirazeni | paralel_prirazeni | nasob_prirazeni;

// musi byt cely vyraz - aby bylo mozne priradit vysledek funkce hodnota | identifikator | funkce
norm_prirazeni : 	promenna ident_prirazeni hodnota;
tern_prirazeni : 	promenna ident_prirazeni podminka 'o_?' hodnota 'sz_:' hodnota;
paralel_prirazeni : promenne ident_prirazeni hodnoty;
//leva strana identifikatory a mezi nimi nemá bıt èárka, ale =
nasob_prirazeni : 	promenne ident_prirazeni hodnota;

//pokud mam jednu promennou u paralelniho prirazeni = norm_prirazeni - otestovat
promenne : promenna | promenne 	'sz_,' promenne;
hodnoty  : hodnota  | hodnoty 	'sz_,'  hodnoty;

podminky: if_vetev ;
if_vetev : 'c_if' 'sz_(' podminka 'sz_)' 'sz_{' prikaz* 'sz_}' | if_vetev else_vetev;
else_vetev : 'c_el' 'sz_{' prikaz* 'sz_}'; 

cykly: for_c | do_while_c | while_c;

for_c : for_syntax 'sz_(' podminka 'sz_)' 'sz_{' prikaz* 'sz_}';
for_syntax: for_i | for_d;
for_i: 'l_for+';
for_d:  'l_for-';

while_c: 'l_whi' 'sz_(' podminka 'sz_)' 'sz_{' prikaz* 'sz_}';
do_while_c: 'l_do' 'sz_{' prikaz* 'sz_}' 'l_whi' 'sz_(' podminka 'sz_)' 'sz_com';

ret: 'sz_ret' ?faktor;

podminka 	: vyraz o_porovnani vyraz;
vyraz 		: (o_scitace)? term ((o_scitace | 'o_||') term)*;
term 		: faktor ((o_nasobice | 'o_&&') faktor)*;
faktor   	: hodnota | promenna | 'sz_(' vyraz 'sz_)';

o_porovnani : 'o_==' | 'o_>' | 'o_>=' | 'o_<' | 'o_<=' | 'o_!=';
o_scitace : 'o_+' | 'o_-' | 'o_u-';
o_nasobice : 'o_/' | 'o_*';
o_negace : 'o_!';
o_logicke : 'o_&&' | 'o_||'; 

ident_prirazeni : 'o_=';
    
NUM : [0-9]+;
BOOL : [0-1];
WS : [ \t\r\n]+ -> skip ;// skip spaces, tabs, newlines
