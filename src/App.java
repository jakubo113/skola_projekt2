import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		Tovarna tovarna = new Tovarna();

		Scanner sc = new Scanner(System.in);

		String typ = null, pokracovat = "a", nacitatSroby, vytvoritStroje;
		int id, moznost, sroby, podlozky;
		
		System.out.print("Chcete vytvorit stroje? [A/n]: ");
		vytvoritStroje = sc.nextLine();
		

		if (vytvoritStroje.toLowerCase().equals("a")) {
			do {
				System.out.print("Vyber si typ stroja (A/B/C): ");
				
				typ = sc.nextLine();
	
				if (!typ.toUpperCase().equals(A.TYP) && !typ.toUpperCase().equals(B.TYP)
						&& !typ.toUpperCase().equals(C.TYP)) {
					System.out.println("Nezadal si spravny typ stroja");
					continue;
				}
				
				System.out.print("Identifikator stroja: ");
				id = sc.nextInt();
				sc.nextLine();
	
				if (typ.toUpperCase().equals(A.TYP)) {
					tovarna.pridajStroj(new A(id));
				} else if (typ.toUpperCase().equals(B.TYP)) {
					tovarna.pridajStroj(new B(id));
				} else if (typ.toUpperCase().equals(C.TYP)) {
					tovarna.pridajStroj(new C(id));
				} else {
					System.out.println("Nezadal si spravny typ stroja!");
				}
	
				System.out.print("Pokracovat? [A/n]: ");
				pokracovat = sc.nextLine();
			} while (pokracovat.toLowerCase().equals("a"));
		}
		
		while (true) {
			System.out.println("1 - Vypis vsetky stroje");
			System.out.println("2 - Zadaj pracu");
			System.out.println("3 - Rusenie prace strojov");
			System.out.println("4 - Zmazat stroj");
			System.out.println("5 - pokazit stroj");
			System.out.println("6 - opravit stroj");
			System.out.println("7 - ulozit do suboru");
			System.out.println("8 - nacitat zo suboru");
			System.out.println("0 - Koniec");
			System.out.print("Zadaj moznost: ");

			moznost = sc.nextInt();
			sc.nextLine();

			if (moznost == 0) {
				break;
			}

			if (moznost == 1) {
				System.out.println("Pocet strojov typu A: " + tovarna.pocetStrojovTypu(A.TYP));
				System.out.println("Aktualna volna kapacita: " + tovarna.getKapacita(A.TYP));
				System.out.println("===============");
				System.out.println("Pocet strojov typu B: " + tovarna.pocetStrojovTypu(B.TYP));
				System.out.println("Aktualna volna kapacita: " + tovarna.getKapacita(B.TYP));
				System.out.println("Pocet vyrabanych srobov: " + tovarna.getSroby());
				System.out.println("===============");
				System.out.println("Pocet strojov typu C: " + tovarna.pocetStrojovTypu(C.TYP));
				System.out.println("Aktualna volna kapacita: " + tovarna.getKapacita(C.TYP));
				System.out.print("\n\n");
				System.out.println("Celkova energeticka narocnost: " + tovarna.getEnergetickaNarocnost());
				System.out.println("Celkove mnozstvo vyrabanych suciastok: " + tovarna.getMnozstvoSuciastok());
			} else if (moznost == 2) {
				System.out.print("Chcete vytvarat sroby? [A/n] ");
				nacitatSroby = sc.nextLine();

				if (nacitatSroby.toLowerCase().equals("a")) {
					System.out.print("kolko srobov za hodinu?");
					sroby = sc.nextInt();
					sc.nextLine();
					tovarna.porovnajSroby(sroby);
				}

				System.out.print("Kolko podloziek za hodinu?");
				podlozky = sc.nextInt();
				tovarna.porovnajPodlozky(podlozky);
			} else if (moznost == 3) {
				System.out.print("Chete rusit sroby[s] alebo podlozky[p]: ");
				String volba = sc.nextLine();
				System.out.print("Pocet: ");
				int pocetPoloziek = Integer.parseInt(sc.nextLine());
				if (volba.equals("s")) {
					tovarna.odcitajSroby(pocetPoloziek);
				} else if (volba.equals("p")) {
					tovarna.odcitajPodlozky(pocetPoloziek);
				} else {

				}
			} else if (moznost == 4) {
				System.out.print("Zadaj id stroja: ");
				int id_stroja = Integer.parseInt(sc.nextLine());
				tovarna.zmazatStroj(id_stroja);
			} else if (moznost == 5) {
				System.out.print("Zadaj id stroja: ");
				int id_stroja = Integer.parseInt(sc.nextLine());
				tovarna.pokazitStroj(id_stroja);
			} else if (moznost == 6) {
				System.out.print("Zadaj id stroja: ");
				int id_stroja = Integer.parseInt(sc.nextLine());
				tovarna.opravitStroj(id_stroja);
			} else if (moznost == 7) {
				FileOutputStream fo;
				try {
					fo = new FileOutputStream("db.txt");
					ObjectOutputStream os = new ObjectOutputStream(fo);
					os.writeObject(tovarna);
					os.close();
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (moznost == 8) {
				try {
					FileInputStream fileIn = new FileInputStream("db.txt");
					ObjectInputStream in = new ObjectInputStream(fileIn);
					tovarna = (Tovarna) in.readObject();
					in.close();
					fileIn.close();
				} catch (IOException i) {
					i.printStackTrace();
					return;
				} catch (ClassNotFoundException c) {
					System.out.println("Employee class not found");
					c.printStackTrace();
					return;
				}

			}
		}
		;

		sc.close();
	}

}
