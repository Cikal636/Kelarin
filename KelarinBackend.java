public class KelarinBackend {
    public static void main(String[] args) {
        ManajerTugas manajer = new ManajerTugas();

        try {
            // Tambah tugas
            Tugas t1 = new Tugas("Belajar OOP", "Mengerjakan tugas Java", Prioritas.TINGGI, "15-12-2025");
            Tugas t2 = new Tugas("Olahraga", "Jogging pagi", Prioritas.SEDANG, "16-12-2025");
            Tugas t3 = new Tugas("Belanja", "Beli bahan makanan", Prioritas.RENDAH, "14-12-2025");

            manajer.tambahTugas(t1);
            manajer.tambahTugas(t2);
            manajer.tambahTugas(t3);

            // Tampilkan semua tugas
            System.out.println("Daftar Tugas:");
            manajer.tampilkanTugas();

            // Sorting by deadline
            System.out.println("\nSetelah sorting deadline:");
            manajer.sortByDeadline();
            manajer.tampilkanTugas();

            // Sorting by prioritas
            System.out.println("\nSetelah sorting prioritas:");
            manajer.sortByPrioritas();
            manajer.tampilkanTugas();

            // Edit tugas
            Tugas tugasBaru = new Tugas("Belajar UML", "Membuat diagram UML", Prioritas.TINGGI, "17-12-2025");
            manajer.editTugas(0, tugasBaru);

            System.out.println("\nSetelah edit tugas index 0:");
            manajer.tampilkanTugas();

            // Hapus tugas
            manajer.hapusTugas(1);
            System.out.println("\nSetelah hapus tugas index 1:");
            manajer.tampilkanTugas();

        } catch (DeadlineInvalidException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}