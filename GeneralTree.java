import java.util.LinkedList;

public class GeneralTree {

    private class Node {

        // Atributos da classe Node
        public Node pai;
        public Integer terras;
        public String nome; 
        public LinkedList<Node> filhos;

        // Metodos da classe Node
        public Node(Integer terras, String nome) {
            pai = null;
            this.terras = terras;
            this.nome = nome;
            filhos = new LinkedList<>();
        }

        private void addSubtree(Node n) {
            n.pai = this;
            filhos.add(n);
        }

        private boolean removeSubtree(Node n) {
            n.pai = null;
            return filhos.remove(n);
        }

        public Node getSubtree(int i) {
            if ((i < 0) || (i >= filhos.size())) {
                throw new IndexOutOfBoundsException();
            }
            return filhos.get(i);
        }

        public int getSubtreesSize() {
            return filhos.size();
        }
    }

    // Atributos da classe GeneralTreeOfIntegerAndString
    private Node root;
    private int count;

    // Metodos da classe GeneralTreeOfIntegerAndString

    /**
     * Metodo construtor
     */
    public GeneralTree() {
        root = null;
        count = 0;
    }

    /**
     * Retorna o numero total de elementos da arvore.
     * @return count
     */
    public int size() {
        return count;
    }

    /**
     * Procura por 'nome' a partir de 'n', seguindo um caminhamento pre-fixado. Retorna a referencia para
     * o nodo no qual 'nome' esta armazenado. Se não encontrar 'nome', retorna NULL.
     * @param nome nome contido no nodo que estamos procurando
     * @param n nodo por onde comecamos a procurar
     * @return referencia para o nodo que contem o 'nome' como elemento
     */
    private Node searchNodeRef(String nome, Node n) {
        if (n == null) {
            return null;
        }
        // visita a raiz
        if(nome.equals(n.nome)) {// se nome esta no nodo n
            return n; // retorna a referencia para n
        }
        else { // visita os filhos
            Node aux = null;
            for (int i=0; i<n.getSubtreesSize() && aux==null; i++) {
                aux = searchNodeRef(nome, n.getSubtree(i));
            }
            return aux;
        }
    }

    /**
     * Adiciona um novo nodo na arvore
     * @param nome Nome de quem possui as terras
     * @param terras Quantidade de terras 
     * @param nPai Pai da pessoa que foi adicionada
     * @return True se adicionou a pessoa e False caso o contrário
     */
    public boolean add(String nome, Integer terras, String nPai) {
        // Primeiro cria o nodo
        Node n = new Node(terras, nome);
        // Verifica se eh para inserir como raiz
        if (nPai == null) { // se a arvore nao estiver vazia
            if (root != null) {
                n.addSubtree(root); 
                root.pai = n;
            }
            root = n;
            count++;
            return true;
        }
        else { // inserir "nome" como filho de "nPai"
            Node aux = searchNodeRef(nPai, root); // Procura por nPai
            if (aux != null) { // se achou nPai
                aux.addSubtree(n); // aux esta apontando para o nodo no qual nPai esta armazenado
                n.pai = aux;
                count++;
                return true;
            }
        return false;
        }
    }

    /**
     * Veririfica se a pessoa está na arvore
     * @param nome Nome da pessoa que se quer verificar a presenca
     * @return True se a pessoa estiver na arvore e False caso o contrario
     */
    public boolean contains (String nome) {
        Node aux = this.searchNodeRef(nome, root);
        if (aux == null) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Metodo de caminhamento entre os valores da arvore
     * @return lista de elementos da arvore na ordem de caminhamento positionsWidth
     */
    public LinkedList<String> positionsWidth() {
        LinkedList<String> lista = new LinkedList<>();
        
        if (root != null) { // se a arvore nao estiver vazia
            Queue<Node> fila = new Queue<>(); // cria fila de nodos
            // Primeiro coloca a raiz na fila
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                // Retira da fila
                Node aux = fila.dequeue();
                // Coloca o elemento na lista
                lista.add(aux.nome);
                // Coloca os filhos na fila
                for (int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
        }
        return lista;
    }

    /**
     * Metodo de caminhamento entre os valores da arvore
     * @return lista de elementos da arvore na ordem de caminhamento positionsPre
     */
    public LinkedList<String> positionsPre() {  
        LinkedList<String> lista = new LinkedList<>();
        positionsPreAux(root,lista);
        return lista;
    }  
    
    private void positionsPreAux(Node n, LinkedList<String> lista) {
        if (n != null) {
            // Visita a raiz
            lista.add(n.nome);
            // Visita os filhos
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i), lista);
            }
        }
    }

    /**
     * Metodo de caminhamento entre os valores da arvore
     * @return lista de elementos da arvore na ordem de caminhamento positionsPos
     */
    public LinkedList<String> positionsPos() {  
        LinkedList<String> lista = new LinkedList<>();
        positionsPosAux(root, lista);
        return lista;
    }  
    
    private void positionsPosAux(Node n, LinkedList<String> lista) {
        if (n != null) { // visita os filhos
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i), lista);
            }
            lista.add(n.nome);
        }
    }

    /**
     * Retorna o level na qual se encontra o nodo que contem o nome
     * @param nome Nome da pessoa que se esta procurando o level
     * @return O level que essa pessoa se encontra
     */
    public int level(String nome) {
        // Referencia para o nodo
        Node aux = searchNodeRef(nome, root);
        // Contador
        int i = 0;
        
        while (aux != root) {
            aux = aux.pai;
            i++;
        }
        return i;
    }

    /**
     * Remove a arvore que comeca no nome 
     * @param nome nome da pessoa que esta na raiz da subarvore a ser removida
     * @return True se removeu a subarvore e False caso o contrario
     */
    public boolean removeBranch(String nome) { 
        if (root == null) {
            return false;
        }
        
        // Se element estiver na raiz
        if (nome.equals(root.nome)) {
            root = null;
            count = 0;
            return true;
        }
        
        Node aux = this.searchNodeRef(nome, root);
        if (aux == null) { // se nao encontrou element
            return false;
        }
        
        Node pai = aux.pai;
        pai.removeSubtree(aux);
        aux.pai = null;
        count = count - countNodes(aux);
        return true;
    }

    /**
     * Conta a quantidade de nodos na arvore a partir do nodo utilizado como parametro. Para contar todos os 
     * elementos da arvore, o nodo deve ser a raiz.
     * @param n Nodo que se comeca a contar os nodos da arvore. Deve ser o root.
     * @return o numero de nodos na arvore
     */
    private int countNodes(Node n) {
        if (n == null) {
            return 0;
        }
        int c = 0;
        for(int i=0; i<n.getSubtreesSize(); i++) {
            c = c + countNodes(n.getSubtree(i));
        }
        return c + 1;   
    }

    /**
     * Verifica se um nodo eh externo/folha.
     * @param nome Nome da pessoa que se quer saber se esta em um nodo folha ou nao
     * @return True se for um nodo folha e false caso o contrario
     */
    public boolean isExternal(String nome) {
        Node aux = this.searchNodeRef(nome, root);
        if (aux == null) { // se nao encontrou o nome
            return false;
        }
        if (aux.getSubtreesSize() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Verifica se um nodo eh interno/tem filhos
     * @param nome Nome da pessoa que se quer saber se o nodo eh interno ou nao
     * @return True se for interno e False caso o contrario
     */
    public boolean isInternal(String nome) {
        Node aux = this.searchNodeRef(nome, root);
        if (aux == null) {
            return false;
        }
        if (aux.getSubtreesSize() == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Mostra a quantidade de terras de um determinado nodo
     * @param nome Nome da pessoa que queremos saber a qtd de terras
     * @param nPai Nome do pai da pessoa que queremos saber a qtd de terras
     * @return -1 se não encontrar a pessoa ou a quantidade de terras da pessoa
     */
    public int qtd_terras(String nome, String nPai) {
        Node aux = this.searchNodeRef(nome, root);

        if (aux == null) { // se não encontrou o nome
            return -1;
        }
        else {
            return aux.terras;
        }
    }  

    /**
     * Procura o nodo com maior numero de filhos
     * @return Numero de filhos do nodo com maior numero de filhos
     */
    public int getMaxChildren() {
        return getMaxChildren(root,0);
    }

    private int getMaxChildren(Node n, int numFilhos) {
        if (n == null)
            return numFilhos;
        
        if (n.getSubtreesSize() == 0) // se "n" nao tem filho
            return numFilhos;
        
        // Verifica se "n" tem um numero de filhos maior que
        // numFilhos, e então atualiza numFilhos
        if (n.getSubtreesSize() > numFilhos) {
            numFilhos = n.getSubtreesSize();
        }
        
        // Chama este metodo recursivamente para cada filho
        for (int i=0; i<n.getSubtreesSize(); i++) {
            numFilhos = getMaxChildren(n.getSubtree(i), numFilhos);
        }
        return numFilhos;
    }

    /**
     * Coloca a root e os o primeiro filho em uma arvore, a partir das duas primeiras linhas do arquivo 
     * que sera utilizado na aplicacao.
     * @param linha1 primeira linha do arquivo, contem o numero de terras do pai
     * @param linha2 segunda linha do arquivo, contem o nome do pai, do filho e numero de terras do filho
     */
    public void LeInicio(String linha1, String linha2) {

        // Pegamos o numero da linha 1, transformamos em int e atribuimos a variavel terrasPai
        int terrasPai = Integer.parseInt(linha1);

        // Criamos um vetor para armezanar as tres informacoes da linha 2
        String infos[] = linha2.split(" ");
        
        // Criamos as variaveis que serao utilizadas para iniciar a arvore
        String nomePai = infos[0];
        String nomeFilho = infos[1];
        int terrasFilho = Integer.parseInt(infos[2]);

        // Adicionamos as duas primeiras pessoas na arvore
        add(nomePai, terrasPai, null); // Adicionamos a raiz na arvore
        add(nomeFilho, terrasFilho, nomePai); // Adicionamos o primeiro filho
    }

    /**
     * Divide a quantidade de terras do pai pela quantidade de filhos e distribui para cada um
     * deles a sua heranca
     */
    public void heranca() {
        // Lista com os nomes em ordem de nivel
        LinkedList<String> lista = positionsWidth();
    // Interage com cada elemento da lista atribuindo as terras 
        for (int i=0; i<lista.size(); i++) {
            Node elem = searchNodeRef(lista.get(i), root);
            if (elem != root) {
                int herenca = (elem.pai.terras / elem.pai.getSubtreesSize());
                elem.terras += herenca;
            }
        }
    }

    /**
     * Cria uma lista com o nome do guerreiro e a quantidade de terras que ele tem
     * @return lista com os guerreiros
     */
    public LinkedList<String> guerreiros() {
        LinkedList<String> lista = new LinkedList<>();
        
        if (root != null) { // se a arvore nao estiver vazia
            Queue<Node> fila = new Queue<>(); // cria fila de nodos
            // Primeiro coloca a raiz na fila
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                // Retira da fila
                Node aux = fila.dequeue();
                // Cria a string de terras
                String terras = Integer.toString(aux.terras);
                // Coloca o elemento na lista
                lista.add(aux.nome + ": " + terras);
                // Coloca os filhos na fila
                for (int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
        }
        return lista;
    }

    

    /**
     * Retorna o primeiro guerreiro na arvore genealogica
     * @return nome da raiz
     */
    public String pai() {
        return root.nome;
    }

    /**
     * Gera o desenho dos nodos para serem mostrados no site GraphViz
     * @param n Nodo que se quer desenhar
     */
    public void geraNodosDOT(Node n)
    {
        System.out.println("node [shape = circle];\n");
        
        LinkedList<String> L = new LinkedList<>();
        L = positionsWidth(); 
        LinkedList<String> W = new LinkedList<>();
        W = guerreiros(); 

        for (int i = 0; i< L.size(); i++ )
        {
            // node1 [label = "1"]
            System.out.println("node" + L.get(i) + " [label = \"" +  W.get(i) + "\"]") ;
        }
    }

    public void geraConexoesDOT(Node n)
    {
        for (int i=0; i<n.getSubtreesSize(); i++)
        {
            Node aux = n.getSubtree(i);
            System.out.println("node" + n.nome + " -> " + "node" + aux.nome + ";");
            geraConexoesDOT(aux);
        }
    }

    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline 
    public void geraDOT()
    {
        System.out.println("digraph g { \n");
        // node [style=filled];
        
        geraNodosDOT(root);
        
        geraConexoesDOT(root);
        System.out.println("}\n");
    }
}