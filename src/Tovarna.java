import java.io.Serializable;
import java.util.ArrayList;

public class Tovarna implements Serializable {

	private ArrayList<StrojInterface> stroje = new ArrayList<>();

	private Integer volnyPriestorA = 0;
	private Integer kapacitaAMax = 0;

	private Integer volnyPriestorB = 0;
	private Integer kapacitaBMax = 0;

	private Integer volnyPriestorC = 0;
	private Integer kapacitaCMax = 0;

	private Integer sroby = 0;

	public void pridajStroj(StrojInterface stroj) {
		if (idExistuje(stroj.getId())) {
			System.out.println("Id uz existuje!");
			return;
		}

		if (pocetStrojovTypu(C.TYP) < 1 || !stroj.getTyp().equals(C.TYP)) {
			stroje.add(stroj);
			inkrementujKapacitu(stroj);
		} else {
			System.out.println("Stroj typu C moze byt iba jeden!");
		}
	}

	public int getKapacita(String typ) {
		int kapacita = 0;

		switch (typ) {
		case A.TYP:
			kapacita = volnyPriestorA;
			break;
		case B.TYP:
			kapacita = volnyPriestorB;
			break;
		case C.TYP:
			kapacita = volnyPriestorC;
			break;
		}

		return kapacita;
	}

	public void porovnajSroby(int sroby) {
		if (volnyPriestorB >= sroby) {
			volnyPriestorB -= sroby;
			this.sroby += sroby;
		} else {
			System.out.println("Stroj B nema dostatocnu kapacitu");
		}
	}

	public void porovnajPodlozky(int podlozky) {
		if (podlozky > (volnyPriestorA + volnyPriestorB + volnyPriestorC)) {
			System.out.println("Nemas dostatok strojov!");
			return;
		}

		while (podlozky > 0) {
			if (podlozky > 0 && volnyPriestorC != 0) {
				if (podlozky > volnyPriestorC) {
					podlozky -= volnyPriestorC;
					volnyPriestorC = 0;
				} else {
					int hodnotaC = volnyPriestorC;
					volnyPriestorC -= podlozky;
					podlozky -= hodnotaC;
				}
			} else if (podlozky > 0 && volnyPriestorB != 0) {
				if (podlozky > volnyPriestorB) {
					podlozky -= volnyPriestorB;
					volnyPriestorB = 0;
				} else {
					int hodnotaB = volnyPriestorB;
					volnyPriestorB -= podlozky;
					podlozky -= hodnotaB;
				}
			} else if (podlozky > 0 && volnyPriestorA != 0) {
				if (podlozky > volnyPriestorA) {
					podlozky -= volnyPriestorA;
					volnyPriestorA = 0;
				} else {
					int hodnotaA = volnyPriestorA;
					volnyPriestorA -= podlozky;
					podlozky -= hodnotaA;
				}
			}
		}
	}

	public int pocetStrojovTypu(String typ) {
		int pocitadlo = 0;

		for (StrojInterface stroj : stroje) {
			if (stroj.getTyp().equals(typ)) {
				pocitadlo++;
			}
		}

		return pocitadlo;
	}

	private boolean idExistuje(int id) {
		for (StrojInterface stroj : stroje) {
			if (stroj.getId() == id) {
				return true;
			}
		}

		return false;
	}

	private void inkrementujKapacitu(StrojInterface stroj) {
		switch (stroj.getTyp()) {
		case A.TYP:
			volnyPriestorA += stroj.getKapacita();
			kapacitaAMax += stroj.getKapacita();
			break;
		case B.TYP:
			volnyPriestorB += stroj.getKapacita();
			kapacitaBMax += stroj.getKapacita();
			break;
		case C.TYP:
			volnyPriestorC += stroj.getKapacita();
			kapacitaCMax += stroj.getKapacita();
			break;
		}
	}

	public int getSroby() {
		return sroby;
	}

	public void odcitajSroby(int pocetSrobov) {
		if (sroby < pocetSrobov) {
			System.out.println("Nieje mozne znizit pocet vyroby srobov. Zadali ste velke mnozstvo.");
			return;
		}

		volnyPriestorB += pocetSrobov;
		sroby -= pocetSrobov;
	}

	public void odcitajPodlozky(int pocetPoloziek) {
		if (pocetPoloziek > (kapacitaAMax - volnyPriestorA + kapacitaBMax - sroby - volnyPriestorB + kapacitaCMax
				- volnyPriestorC)) {
			System.out.println("Nieje mozne znizit pocet vyroby podloziek. Zadali ste velke mnozstvo.");
			return;
		}

		while (pocetPoloziek > 0) {
			if (volnyPriestorA < kapacitaAMax) {
				int pocetNaOdpocitanie = (pocetPoloziek <= kapacitaAMax - volnyPriestorA) ? pocetPoloziek
						: kapacitaAMax - volnyPriestorA;
				volnyPriestorA += pocetNaOdpocitanie;
				pocetPoloziek -= pocetNaOdpocitanie;
				// sroby moze odcitat v pripade, ze chcem odcitat menej srobov
				// ako je volna kapacita - sroby
			} else if (volnyPriestorB < kapacitaBMax && sroby < kapacitaBMax - volnyPriestorB) {
				int pocetNaOdpocitanie = (pocetPoloziek <= kapacitaBMax - volnyPriestorB - sroby) ? pocetPoloziek
						: kapacitaBMax - volnyPriestorB - sroby;
				volnyPriestorB += pocetNaOdpocitanie;
				pocetPoloziek -= pocetNaOdpocitanie;
			} else if (volnyPriestorC < kapacitaCMax) {
				int pocetNaOdpocitanie = (pocetPoloziek <= kapacitaCMax - volnyPriestorC) ? pocetPoloziek
						: kapacitaCMax - volnyPriestorC;
				volnyPriestorC += pocetNaOdpocitanie;
				pocetPoloziek -= pocetNaOdpocitanie;
			}
		}
	}

	public void zmazatStroj(int id) {
		StrojInterface najdenyStroj = prerozdelitVyrabaneSuciastky(id);
		if (najdenyStroj != null) {
			zmazatStrojZListu(najdenyStroj.getId());
		}
	}

	private StrojInterface prerozdelitVyrabaneSuciastky(int id) {
		StrojInterface najdenyStroj = null;
		ArrayList<Integer> id_strojov = new ArrayList<Integer>();
		
		int praca = 0;
		int vyrabaneSroby = 0;

		for (StrojInterface stroj : stroje) {
			if (id == stroj.getId()) {
				najdenyStroj = stroj;
			}
		}

		if (najdenyStroj == null) {
			System.out.println("Takyto stroj neexistuje!");
			return null;
		}

		for (StrojInterface stroj : stroje) {
			if (stroj.getTyp() == najdenyStroj.getTyp()) {
				id_strojov.add(stroj.getId());
			}
		}

		String typStroja = najdenyStroj.getTyp();
		int pracujuci = 0;
		//KOLKO STROJOV PRACUJE
		if (najdenyStroj.getTyp().equals(A.TYP)) {
			pracujuci = (int) Math.ceil(((double) kapacitaAMax - volnyPriestorA) / najdenyStroj.getKapacita());
		} else if (najdenyStroj.getTyp().equals(B.TYP)) {
			pracujuci = (int) Math.ceil(((double) kapacitaBMax - volnyPriestorB) / najdenyStroj.getKapacita());
		} else if (najdenyStroj.getTyp().equals(C.TYP)) {
			pracujuci = (int) Math.ceil(((double) kapacitaCMax - volnyPriestorC) / najdenyStroj.getKapacita());
		}
		
		
		
//		if (id_strojov.indexOf(najdenyStroj.getId()) > pracujuci - 1) {
//			praca = 0;
//			volnyPriestor = najdenyStroj.getKapacita();
//		} else {
//			if (id_strojov.indexOf(najdenyStroj.getId()) < pracujuci - 1) {
//				praca = najdenyStroj.getKapacita() - sroby;
//			} else {
//				if (najdenyStroj.getTyp().equals(A.TYP)) {
//					if (volnyPriestorA > 0) {
//						volnyPriestor = pracujuci * najdenyStroj.getKapacita() - (kapacitaAMax - volnyPriestorA);
//						praca = najdenyStroj.getKapacita() - volnyPriestor;
//						//praca = kapacitaAMax - volnyPriestorA;
//					}
//				} else if (najdenyStroj.getTyp().equals(B.TYP)) {
//					if (volnyPriestorB > 0) {
//						volnyPriestor = pracujuci * najdenyStroj.getKapacita() - (kapacitaBMax - volnyPriestorB - sroby);
//						praca = najdenyStroj.getKapacita() - volnyPriestor;
//						//praca = kapacitaBMax - volnyPriestorB - sroby;
//					}
//				} else if (najdenyStroj.getTyp().equals(C.TYP)) {
//					if (volnyPriestorC > 0) {
//						volnyPriestor = pracujuci * najdenyStroj.getKapacita() - (kapacitaCMax - volnyPriestorC);
//						praca = najdenyStroj.getKapacita() - volnyPriestor;
//						//praca = kapacitaCMax - volnyPriestorC;
//					}
//				}
//			}
//		}
		/* -------------------------------------------------------------------*/
		/* -------------------------------------------------------------------*/
		/* -------------------------------------------------------------------*/
		/* -------------------------------------------------------------------*/
		
		//SROBY
				
				int volnyPriestor = 0;
				int i = 1, poradie = 0;
				
				
				if (najdenyStroj.getTyp().equals(B.TYP)) {
					for (int j = 0; j < stroje.size(); j++) {
						if (stroje.get(j).getTyp().equals(B.TYP)) {
							poradie++;
						}
						if (stroje.get(j).getId() == id) {
							break;
						}
					}
					
					int sroby1 = sroby;
					
					if (sroby > 0) {
						while (true) {
							sroby1 -= B.KAPACITA;
							if(poradie == i) {
								break;
							}
							i++;
						}
						
						if ((sroby1 + 50) % 50 == 0) {
							vyrabaneSroby = 50;
						} else if (sroby1 + 50 > 0) {
							vyrabaneSroby = sroby1 + 50;
						} else {
							vyrabaneSroby = 0;
						}
					}
				}
				
				
		//CELKOVA PRACA
				
		int y = 1, miesto = 0, konanaPraca=0;
		
		
		if (najdenyStroj.getTyp().equals(A.TYP)) {
			for (int x = 0; x < stroje.size(); x++) {
				if (stroje.get(x).getTyp().equals(A.TYP)) {
					miesto++;
				}
				if (stroje.get(x).getId() == id) {
					konanaPraca=kapacitaAMax-volnyPriestorA;
					break;
				}
			}
		}
		if (najdenyStroj.getTyp().equals(B.TYP)) {
			for (int x = 0; x < stroje.size(); x++) {
				if (stroje.get(x).getTyp().equals(B.TYP)) {
					miesto++;
				}
				if (stroje.get(x).getId() == id) {
					konanaPraca=kapacitaBMax-volnyPriestorB;
					break;
				}
			}
		}
		if (najdenyStroj.getTyp().equals(C.TYP)) {
			for (int x = 0; x < stroje.size(); x++) {
				if (stroje.get(x).getTyp().equals(C.TYP)) {
					miesto++;
				}
				if (stroje.get(x).getId() == id) {
					konanaPraca=kapacitaCMax-volnyPriestorC;
					break;
				}
			}
		}
			
			
			if (konanaPraca > 0) {
				while (true) {
					konanaPraca -= najdenyStroj.getKapacita();
					if(miesto == y) {
						break;
					}
					y++;
				}
				
				if ((konanaPraca + najdenyStroj.getKapacita()) % najdenyStroj.getKapacita() == 0) {
					praca = najdenyStroj.getKapacita();
					volnyPriestor = 0;
				} else if (konanaPraca + najdenyStroj.getKapacita() > 0) {
					praca = (konanaPraca + najdenyStroj.getKapacita())-vyrabaneSroby;
					volnyPriestor=konanaPraca*(-1);
				} else {
					praca = 0;
					volnyPriestor=najdenyStroj.getKapacita();
				}
			}
			
			
			if(pracujuci == 0){
				if (najdenyStroj.getTyp().equals(A.TYP)) {
					volnyPriestorA -= najdenyStroj.getKapacita();
				} else if (najdenyStroj.getTyp().equals(B.TYP)) {
					volnyPriestorB -= najdenyStroj.getKapacita();
				} else if (najdenyStroj.getTyp().equals(C.TYP)) {
					volnyPriestorC -= najdenyStroj.getKapacita();
				}
			}
		/* -------------------------------------------------------------------*/
		/* -------------------------------------------------------------------*/
		/* -------------------------------------------------------------------*/
		/* -------------------------------------------------------------------*/
//		if (id_strojov.indexOf(najdenyStroj) < id_strojov.size() - 1) {
//			praca = najdenyStroj.getKapacita() - sroby;
//		} else {
//			if (najdenyStroj.getTyp().equals(A.TYP)) {
//				if (volnyPriestorA > 0) {
//					praca = kapacitaAMax - volnyPriestorA;
//				}
//			} else if (najdenyStroj.getTyp().equals(B.TYP)) {
//				if (volnyPriestorB > 0) {
//					praca = kapacitaBMax - volnyPriestorB - sroby;
//				}
//			} else if (najdenyStroj.getTyp().equals(C.TYP)) {
//				if (volnyPriestorC > 0) {
//					praca = kapacitaCMax - volnyPriestorC;
//				}
//			}
//		}
			
			//VLOZENIE DO PREMENEJ V PRIPADE PRIESTORU STROJA KTORY NEBOL UPLNE ZAPLNENY A DOPLNENIE DO PODMIENKY CI SA NAOZAJ STROJ ZMESTI

		if (praca + vyrabaneSroby > volnyPriestorA + volnyPriestorB + volnyPriestorC - volnyPriestor || vyrabaneSroby > volnyPriestorB) {
			System.out.println("Nieje mozne zmazat stroj, pretoze nieje mozne prerozdelit pracu.");
			return null;
		}
		/**********************/
		/**********************/
		/**********************/
		/**********************/
		
		//ODCITANIE VOLNEHO PRIESTORU STROJA KTORY NEBOL UPLNE ZAPLNENY
		if (najdenyStroj.getTyp().equals(A.TYP)) {
			volnyPriestorA -= volnyPriestor;
		} else if (najdenyStroj.getTyp().equals(B.TYP)) {
			volnyPriestorB -= volnyPriestor;
		} else if (najdenyStroj.getTyp().equals(C.TYP)) {
			volnyPriestorC -= volnyPriestor;
		}
		/**********************/
		/**********************/
		/**********************/
		volnyPriestorB -= vyrabaneSroby;
		/***********************/
		
		//ODCITANIE OD MAXIMALNEJ KAPACITY STROJOV
		
		if (typStroja.equals(A.TYP)) {
			kapacitaAMax -= najdenyStroj.getKapacita();
		} else if (typStroja.equals(B.TYP)) {
			kapacitaBMax -= najdenyStroj.getKapacita();
		} else if (typStroja.equals(C.TYP)) {
			kapacitaCMax -= najdenyStroj.getKapacita();
		}
		
//		if (praca == 0) {
//			if (volnyPriestorC != 0) {
//				volnyPriestorC -= volnyPriestor;
//			} else if (volnyPriestorB != 0) {
//				volnyPriestorB -= volnyPriestor;
//			} else if (volnyPriestorA != 0) {
//				volnyPriestorA -= volnyPriestor;
//			}
//		}
		
		
		//DELENIE PRACE
		
		while (praca > 0) {
			if (praca > 0 && volnyPriestorC != 0) {
				if (praca > volnyPriestorC) {
					praca -= volnyPriestorC;
					volnyPriestorC = 0;
				} else {
					int hodnotaC = volnyPriestorC;
					volnyPriestorC -= praca;
					praca -= hodnotaC;
				}
			} else if (praca > 0 && volnyPriestorB != 0) {
				if (praca > volnyPriestorB) {
					praca -= volnyPriestorB;
					volnyPriestorB = 0;
				} else {
					int hodnotaB = volnyPriestorB;
					volnyPriestorB -= praca;
					praca -= hodnotaB;
				}
			} else if (praca > 0 && volnyPriestorA != 0) {
				if (praca > volnyPriestorA) {
					praca -= volnyPriestorA;
					volnyPriestorA = 0;
				} else {
					int hodnotaA = volnyPriestorA;
					volnyPriestorA -= praca;
					praca -= hodnotaA;
				}
			}
		}
		
		return najdenyStroj;
	}

	private void zmazatStrojZListu(int id) {
		for (int i = 0; i < stroje.size(); i++) {
			if (stroje.get(i).getId() == id) {
				stroje.remove(i);
				break;
			}
		}
	}

	public void pokazitStroj(int id) {
		int i = 0;
		
		for (StrojInterface stroj : stroje) {
			if (id == stroj.getId()) {
				if (stroj.getTyp().equals(A.TYP)) {
					System.out.println("Stroj typu A nieje mozne pokazit!");
					return;
				} else {
					stroje.get(i).setPokazeny(true);
				}
			}
			i++;
		}
		
		prerozdelitVyrabaneSuciastky(id);
	}

	public void opravitStroj(int id) {
		StrojInterface najdenyStroj = null;

		for (StrojInterface stroj : stroje) {
			if (id == stroj.getId()) {
				najdenyStroj = stroj;
			}
		}

		if (najdenyStroj.isPokazeny() == false) {
			System.out.println("Tento stroj nieje pokazeny.");
			return;
		}

		if (najdenyStroj.getTyp().equals(A.TYP)) {
			kapacitaAMax += najdenyStroj.getKapacita();
			volnyPriestorA += najdenyStroj.getKapacita();
		} else if (najdenyStroj.getTyp().equals(B.TYP)) {
			kapacitaBMax += najdenyStroj.getKapacita();
			volnyPriestorB += najdenyStroj.getKapacita();
		} else if (najdenyStroj.getTyp().equals(C.TYP)) {
			kapacitaCMax += najdenyStroj.getKapacita();
			volnyPriestorC += najdenyStroj.getKapacita();
		}

		for (int i = 0; i < stroje.size(); i++) {
			if (id == stroje.get(i).getId()) {
				stroje.get(i).setPokazeny(false);
			}
		}
	}

	public int getEnergetickaNarocnost() {
		return (A.ENERGETICKA_NAROCNOST * (kapacitaAMax - volnyPriestorA) 
				+ B.ENERGETICKA_NAROCNOST * (kapacitaBMax - volnyPriestorB)
				+ C.ENERGETICKA_NAROCNOST * (kapacitaCMax - volnyPriestorC));
	}

	public int getMnozstvoSuciastok() {
		return ((kapacitaAMax - volnyPriestorA) + (kapacitaBMax - volnyPriestorB) + (kapacitaCMax - volnyPriestorC));
	}
}
