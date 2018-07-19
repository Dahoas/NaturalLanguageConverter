import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



class	Solution	{
				//public	static	Random	rand	=	new	Random();
				public	static	int	flag	=	0;
				public	static	void	ProcessMethod	(ArrayList<String>	arr,	String	concl,	String	method,	
ArrayList<String>	prem,	ArrayList<String>	premises,	int	rank)	{
								
								String	[]	p	=	new	String	[prem.size()];
								boolean	isForwardStep	=	true;
								for	(int	i	=	0	;	i	<	prem.size()	;	i++)	{
												String	s	=	prem.get(i);
												int	x	=	0;
												//	If	it	is	a	premise	or	an	assumption,	it	is	a	forwards step
												if	(s.indexOf("premise")	>	-1)	{
																int	pindex	=	Integer.parseInt(s.substring(s.indexOf("premise")	+	7));
																pindex--;
																p[i]	=	premises.get(pindex);
												}
												else	if	(s.indexOf("assum")	>	-1)	{
																for	(int	j	=	0	;	j	<	arr.size()	;	j++)	{
																				if	(arr.get(j).indexOf("subproof	id=\""	+	s)	>	-1)	{
																								//	Get	the	statement
																								int	start	=	arr.get(j).indexOf("introduced")	+	12;
																								p[i]	=	arr.get(j).substring(start,	arr.get(j).indexOf("\">"));
																								break;
																				}
																}
												}
												//	If	we	find	out	it	is	a	backwards	step,	set	isForwardStep	to	false
												else	{
																for	(int	j	=	0	;	j	<	arr.size()	;	j++)	{
																				if	(arr.get(j).indexOf("<conclusion	id=\""	+	s)	>	-1)	{
																								//	Find	and	get	rank
																								j--;
																								int	len	=	arr.get(j).length();
																								String	temp	=	arr.get(j).substring(arr.get(j).indexOf("rank=")	+	6,	len	- 2);
																								x	=	Integer.parseInt(temp);
																								//	Get	the	statement	from	that	rank
																								j++;
																								p[i]	=	arr.get(j).substring(arr.get(j).indexOf(">")+1,	arr.get(j).indexOf("</"));
																								break;
																				}
																}
																//	If	the	rank	is	less	than	its	premise,	then	it	is	a	Backwards	step
																if	(rank	<	x)
																				isForwardStep	=	false;
												}
								}
								if	(method.equals("ImpI"))	{
												System.out.println("We	apply	a	backwards	arrow	introduction,	so	that	our	new	goal	is	to	prove	"	+	p[0]	+	".");
												System.out.println();
								}
								else	if	(method.equals("NotI"))	{
												System.out.println("We	proceed	by	refutation.	We	apply	backwards	not	introduction	and	now	our	goal	is	to	prove	a	contradiction,	"	+	p[0]	+	".");
												System.out.println();
								}
								//	This	is	a	forwards step
								if	(isForwardStep)	{
												if	(method.equals("AndI"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("Since	we	have	proven	"	+	p[1]	+	"	and	we	have	proven	"	+	p[0]	+	",	it	follows	that	"	+	concl	+	"	is	proven	as	well.");
																}
																else	{
																				System.out.println("We	know	that	both	"	+	p[0]	+	"	and	"	+	p[1]	+	"	are	true.	Thus,	"	+	concl	+	"	is	true	as	well.");
																}
																System.out.println();
												}
												else	if	(method.equals("AndEL")	||	method.equals("AndER"))	{
																System.out.println("From	"	+	p[0]	+	",	we	know	"	+	concl	+	".");
																System.out.println();
												}
												else	if	(method.equals("OrIR")	||	method.equals("OrIL"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("Since	we	have	shown	"	+	p[0]	+	",	we	can	clearly	introduce	an	\"or\"	to	get	"	+	concl);
																}
																else	{
																				System.out.println("We	know	"	+	p[0]	+	".	With	an	or	introduction,	we	can	introduce	anything	\"or\"ed	with	"	+	p[0]	+	".	Thus,	we	can	to	get	"	+	concl	+	".");
																}
																System.out.println();
												}
												else	if	(method.equals("OrE"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("We	proceed	to	break	up	the	\"or\"	into	two	cases.	For	case	one,	we	assume	the	first	part	in	"	+	p[0]	+	"	and	want	to	show	"	+	concl	+	".	For	case	two,	we	assume	the	second	part	in	"	+	p[0]	+	"	and	want	to	show	"	+	concl	+	".");
																}
																else	{
																				System.out.println("In	order	for	our	goal	to	be	true,	we	must	show	that	both	sides	of	the	or	in	"	+	p[0]	+	"	will	prove	our	goal.	Thus,	we	break	it	into	two	cases	and	proceed.");
																}
																System.out.println();
												}
												else	if	(method.equals("ImpE"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("We	have	shown	that	when	we	assume	"	+	p[0]	+	",	we	can	derive	"	+	p[1]	+	".	Thus,	we	conclude	that	"	+	concl	+	".");
																}
																else	{
																				System.out.println("We	have	shown	that	"	+	p[0]	+	"	implies	"	+	p[1]	+	".	Thus,	we	can	deduce	"	+	concl	+	".");
															 }
																System.out.println();
												}
												else	if	(method.equals("ImpI"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("Since	we	have	shown	"	+	p[0]	+	"	and	"	+ p[1]	+	",	then	we	have	shown	"	+	concl	+	".");
																}
																else	{
																				System.out.println("We	know	that	"	+	p[0]	+	"	implies	"	+	concl	+	",	and	we	know	"	+	p[0]	+	".	Thus,	we	know	"	+	concl	+	".");
																}
																System.out.println();
												}
												else	if	(method.equals("FalsumI"))	{
																System.out.println("Since	we	have	shown	"	+	p[0]	+	"	and	"	+	p[1]	+	",	we	have	successfully	shown	a	contradiction.	Thus,	we	have	a	Falsum.");
																System.out.println();
												}
												else	if	(method.equals("FalsumE"))	{	//	"show	the	negation	of	~M.	instead	say	show	M"
																System.out.println("Since	we	have	shown	a	contradiction,	_|_,	we	can	prove	anything.	Thus,	we	can	show	"	+	concl	+	".");
																System.out.println();
												}
												else	if	(method.equals("IffI"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("Since	we	have	shown	that	"	+	p[0]	+	"	implies	"	+	p[1]	+	",	and	"	+	p[1]	+	"	implies	"	+	p[0]	+	",	it	follows	that	"	+	concl	+	".");
																}
																else	{
																				System.out.println("We	have	shown	"	+	p[0]	+	"	and	vice	versa.	Thus,	by	definition	of	if	and	only	if,	we	can	introduce	the	double	arrow.");
																}
																System.out.println();
												}
												else	if	(method.equals("IffEL")	||	method.equals("IffER"))	{
																System.out.println("Since	we	have	shown	"	+	p[1]	+	",	and	we	know	"	+	p[0]	+	",	it	follows	that	we	have	shown	"	+	concl	+	".");
																System.out.println();
												}
								}
								//	This	is	a	backwards	step
								else	{
												if	(method.equals("AndI"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("From	"	+	concl	+	",	we	know	that	both	"	+	p[0]	+	"	and	"	+	p[1]	+	"	are	true.	Thus,	our	new	goals	are	to	show	both	"	+	p[0]	+	"	and	"	+	p[1]	+	".");
																}
																else	{
																				System.out.println("By	definition	of	the	and,	we	know	both	"	+	p[0]	+	"	and	"	+	p[1]	+	"	are	true.	Working	backwards,	out	new	goal	is	to	show	"	+	p[0]	+	"	and	"	+	p[1]	+	"	are	true	separately.");
																}
																System.out.println();
												}
												else	if	(method.equals("OrIR")	||	method.equals("OrIL"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("We	introduce	an	\"or\"	from	"	+	p[0]	+	"	to	get	"	+	concl	+	".	Thus,	our	new	goal	is	to	show	"	+	p[0]	+	".");
																}
																else	{
																				System.out.println("We	know	that	from	an	or	introduction,	we	can	introduce	any	symbol.	Thus,	we	go	backwards	and	now	our	goal	is	to	show	"	+	p[0]	+	".");
																}
																System.out.println();
												}
												else	if	(method.equals("NotE"))	{	//	,	with	the	assumption	~M.	so	user	knows	whats	the	assumption
																System.out.println("We	proceed	by	refutation.	We	apply	a	backwards	negation	elimination	so	that	our	new	goal	is	to	show	a	contradiction,	"	+	p[0]	+	".");
																System.out.println();
												}
												else	if	(method.equals("ImpE"))	{	//	type	out	premise	so	they	know	whats	the	arrow	eliminating
																System.out.println("We	apply	a	backwards	arrow	elimination,	so	that	our	new	goal	is	to	show	"	+	p[1]	+	".");
																System.out.println();
												}
												else	if	(method.equals("FalsumI"))	{	//	"show	the	negation	of	~M.	instead	say	show	M"
																System.out.println("By	definition	of	a	Falsum,	we	need	to	show	a	contradiction.	Thus,	we	apply	a	backwards	Falsum	introduction	so	that	our	new	goal	is	to	show	the	negation	of	"	+	p[0]	+	"	for	the	contradiction.");
																System.out.println();
												}
												else	if	(method.equals("FalsumE"))	{	//	"show	the	negation	of	~M.	instead	say	show	M"
																System.out.println("From the	Falsum,	we	can	deduce	anything.	Thus,	we	have	shown	"	+	concl	+	".");
																System.out.println();
												}
												else	if	(method.equals("IffI"))	{
																//flag	=	rand.nextInt(100);
																if	(flag	>	50)	{
																				System.out.println("Our	goal	is	to	show	"	+	concl	+	".	Thus,	by	double	arrow	introduction,	our	new	goal	is	to	show	that	"	+	p[1]	+	"	implies	"	+	p[0]	+	",	and	"	+	p[0]	+	"	implies	"	+	p[1]	+	".");
																}
																else	{
																				System.out.println("The	double	arrow	means	that	either	side	implies	the	other.	Thus,	our	new	goal	is	to	break	it	into	two	cases	and	show	that	if	we	assume	"	+	p[1]	+	",	we	can	get	the"	+	p[0]	+	",	and	vice	versa.");
																}
																System.out.println();
												}
												else	if	(method.equals("IffEL")	||	method.equals("IffER"))	{
																System.out.println("Since	we	have	shown	"	+	p[1]	+	",	and	we	know	"	+	p[0]	+	",	it	follows	that	we	have	shown	"	+	concl	+	".");
																System.out.println();
												}
								}
				}
				public	static	void	main(String[]	args)	{
								//	Scanner	from	input
								//Scanner	in	=	new	Scanner(System.in);
								Scanner keyboard = new Scanner(System.in);
								System.out.println("Enter name of file: ");
								String name = keyboard.nextLine();
								Scanner in;
								try {
									in = new Scanner(new FileInputStream(name));
								
								//	Variables
								String	premisesString	=	"";
								String	conclusionString	=	"";
								String	premString	=	"<premise	xref=";
								String	methString	=	"<method	name=";
								String	deriString	=	"</derived>";			
								String	method	=	"";					
								//	Arraylist	to	store	all	the	lines	of	the	file
								ArrayList<String>	arr	=	new	ArrayList<String>();
								ArrayList<String>	premises	=	new	ArrayList<String>();
								//	Scan	all	the	lines	of	the	file	into	the	array
								while	(in.hasNextLine())	{
								 arr.add(in.nextLine());
								}
								//	Find	and	store	all	the	premises	and	just	the	last	conclusion
								for	(int	i	=	0	;	i	<	arr.size()	;	i++)	{
												String	temp	=	arr.get(i);
												int	premStart	=	temp.indexOf("<premise	id=");
												int	premEnd	=	temp.indexOf("</premise>");
												int	conclusionStart	=	temp.indexOf("<conclusion	id=");
												int	conclusionEnd	=	temp.indexOf("</conclusion>");
												
												if	(premStart	>	-1)
																premises.add(temp.substring(premStart	+	23,	premEnd));
												if	(conclusionStart	>	-1)	{
																conclusionString	=	temp.substring(conclusionStart	+	22,	conclusionEnd);
																conclusionString	=	conclusionString.replace(">","");
												}
							}
								System.out.println();
								if	(premises.size()	>	0)	{
												//	Create	premises	string
												for	(int	i	=	0	;	i	<	premises.size()	;	i++)	{
																premisesString	=	premisesString	+	premises.get(i)	+	",	";
											 }
												premisesString	=	premisesString.substring(0,	premisesString.length()-2)	+	".	";
												
												System.out.println("We	have	premises:	"	+	premisesString	+	"Our	goal	is	to	prove:	"	+	conclusionString	+	".");
												System.out.println();
								}
								else	{
												System.out.println("We	start	out	with	no	premises	and	want	to	prove:	"	+	conclusionString	+	".");
												System.out.println();
								}
								//	Variables
								int	n	=	arr.size();
								String	rankString;
								int	upperBound	=	100;	//	Arbitrary	upper	bound,	for	this	we	should	actually	find	the	highest	rank	and	stop
								for	(int	rank	=	0	;	rank	<	upperBound	;	rank++)	{
								 //	Update	rank
								 rankString	=	"derived	rank=\""	+	rank	+ "\"";
								//	Iterate	through	to	find	the	next	lowest	rank
								for	(int	i	=	0	;	i	<	n	;	i++)	{
								 if	(arr.get(i).indexOf(rankString)	>	-1)	{
																				String	concl	=	arr.get(++i);
																				concl	=	concl.substring(concl.indexOf(">")+1,	concl.indexOf("</"));
																				ArrayList<String>	prem	=	new	ArrayList<String>();
																				i++;
																				for	(int	j	=	i	;	arr.get(j).indexOf(deriString)	<	0	;	j++)	{
																								//	Get	Premise
																								int	premI	=	arr.get(j).indexOf(premString);
																								if	(premI	>	-1)	{
																												int	len	=	arr.get(j).length();
																												prem.add(arr.get(j).substring(premI	+	15,	len	- 4));
																								}
																								//	Get	method
																								int	methodI	=	arr.get(j).indexOf(methString);
																								if	(methodI	>	-1)	{
																												int	len	=	arr.get(j).length();
																												method	=	arr.get(j).substring(methodI	+	14,	len	- 4);
																												ProcessMethod(arr,	concl,	method,	prem,	premises,	rank);
																								}
																				}
								 }
								}
				}
								//	Print	End	String
								System.out.println("Based	on	the	above	reasoning,	we	have	a	valid	proof.	Thus,	we	have	shown:	"	+	conclusionString	+	".	End	of	proof.");
								System.out.println();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				}
}